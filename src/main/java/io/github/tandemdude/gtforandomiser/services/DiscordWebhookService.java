package io.github.tandemdude.gtforandomiser.services;

import io.github.tandemdude.gtforandomiser.models.db.DailySubmission;
import io.github.tandemdude.gtforandomiser.models.discord.Interaction;
import io.github.tandemdude.gtforandomiser.models.discord.User;
import io.github.tandemdude.gtforandomiser.models.responses.DiscordFailedInteractionResponseData;
import io.github.tandemdude.gtforandomiser.models.responses.DiscordInteractionResponse;
import io.github.tandemdude.gtforandomiser.models.responses.DiscordSuccessfulInteractionResponseData;
import io.github.tandemdude.gtforandomiser.repositories.DailySubmissionRepository;
import org.bouncycastle.asn1.edec.EdECObjectIdentifiers;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.Security;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;
import java.util.HexFormat;
import java.util.Optional;

@Service
public class DiscordWebhookService {
    private final HexFormat hex = HexFormat.of();

    private final DailySubmissionRepository submissionRepository;

    @Value("${DISCORD_PUBLIC_KEY}")
    private String publicKey;
    @Value("${DAILY_SUBMISSION_MODERATOR_ROLE_ID}")
    private Long moderatorRoleId;

    public DiscordWebhookService(DailySubmissionRepository submissionRepository) {
        this.submissionRepository = submissionRepository;
    }

    public boolean validateWebhookSignature(String payload, String signature, String timestamp) {
        try {
            final var provider = new BouncyCastleProvider();
            Security.addProvider(provider);
            final var publicKeyInfo = new SubjectPublicKeyInfo(
                    new AlgorithmIdentifier(EdECObjectIdentifiers.id_Ed25519), hex.parseHex(publicKey)
            );
            final var publickeySpec = new X509EncodedKeySpec(publicKeyInfo.getEncoded());
            final var keyFactory = KeyFactory.getInstance("ed25519", provider);
            final var signedData = Signature.getInstance("ed25519", provider);
            signedData.initVerify(keyFactory.generatePublic(publickeySpec));
            signedData.update(timestamp.getBytes(StandardCharsets.UTF_8));
            signedData.update(payload.getBytes(StandardCharsets.UTF_8));
            return signedData.verify(hex.parseHex(signature));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Transactional
    public DiscordInteractionResponse<?> handleInteraction(Interaction interaction) {
        if (!interaction.getMember().getRoles().contains(moderatorRoleId)) {
            return new DiscordInteractionResponse<>(
                    4, // MessageCreate
                    new DiscordFailedInteractionResponseData(
                            "You do not have permission to verify/reject runs."
                    )
            );
        }

        String[] customIdParts = interaction.getData().getCustom_id().split("_");
        boolean runAccepted = customIdParts[0].equals("verify");
        long runId = Long.parseLong(customIdParts[1]);

        Optional<DailySubmission> retrievedSubmission = submissionRepository.findById(runId);
        if (retrievedSubmission.isEmpty()) {
            return new DiscordInteractionResponse<>(
                    4,
                    new DiscordSuccessfulInteractionResponseData(
                            String.format("Run with ID `%s` was not found", runId)
                    )
            );
        }
        DailySubmission submission = retrievedSubmission.get();
        if (runAccepted) {
            submission.setVerified(true);
            submissionRepository.save(submission);
        } else {
            submissionRepository.delete(submission);
        }

        User interactionAuthor = interaction.getAuthor();
        return new DiscordInteractionResponse<>(
                7, // Message update
                new DiscordSuccessfulInteractionResponseData(
                        String.format(
                                "Run ID `%s` %s by `%s`",
                                runId, runAccepted ? "verified" : "rejected", interactionAuthor.toString()
                        )
                )
        );
    }
}
