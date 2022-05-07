package io.github.tandemdude.gtforandomiser.services;

import io.github.tandemdude.gtforandomiser.models.db.Daily;
import io.github.tandemdude.gtforandomiser.models.db.DailySubmission;
import io.github.tandemdude.gtforandomiser.models.db.User;
import io.github.tandemdude.gtforandomiser.models.discord.MessageCreateActionRowComponent;
import io.github.tandemdude.gtforandomiser.models.discord.MessageCreateButtonComponent;
import io.github.tandemdude.gtforandomiser.models.discord.MessageCreateEmbed;
import io.github.tandemdude.gtforandomiser.models.discord.MessageCreatePayload;
import io.github.tandemdude.gtforandomiser.models.exceptions.UserNotFoundException;
import io.github.tandemdude.gtforandomiser.repositories.DailyRepository;
import io.github.tandemdude.gtforandomiser.repositories.DailySubmissionRepository;
import io.github.tandemdude.gtforandomiser.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class DailyService {
    private final static String createMessageEndpoint = "https://discord.com/api/channels/%s/messages";

    private final LoadoutGeneratorService loadoutGenerator;
    private final DailyRepository dailyRepository;
    private final DailySubmissionRepository dailySubmissionRepository;
    private final UserRepository userRepository;

    @Value("${DISCORD_BOT_TOKEN}")
    private String botToken;
    @Value("${DAILY_SUBMISSION_CHANNEL_ID}")
    private long submissionChannelId;
    @Value("${DAILY_SUBMISSION_MODERATOR_ROLE_ID}")
    private long submissionModeratorRoleId;

    public DailyService(LoadoutGeneratorService loadoutGenerator, DailyRepository dailyRepository, DailySubmissionRepository dailySubmissionRepository, UserRepository userRepository) {
        this.loadoutGenerator = loadoutGenerator;
        this.dailyRepository = dailyRepository;
        this.dailySubmissionRepository = dailySubmissionRepository;
        this.userRepository = userRepository;
    }

    public String getCurrentDateString() {
        return LocalDate.now().toString();
    }

    public Daily getCurrentDaily() {
        String currentDate = getCurrentDateString();
        Optional<Daily> retrievedDaily = dailyRepository.findById(currentDate);

        Daily currentDaily;
        if (retrievedDaily.isEmpty()) {
            int currentRundownId = loadoutGenerator.getCurrentRundownId();
            currentDaily = dailyRepository.save(
                    new Daily(
                            currentDate,
                            loadoutGenerator.getRandomFullLoadout(currentRundownId),
                            loadoutGenerator.getRandomStageAndDifficulty(currentRundownId)
                    )
            );
        } else {
            currentDaily = retrievedDaily.get();
        }
        return currentDaily;
    }

    public Optional<Daily> getYesterdaysDaily() {
        return getDailyForDateString(LocalDate.now().minusDays(1).toString());
    }

    public Optional<Daily> getDailyForDateString(String date) {
        return dailyRepository.findById(date);
    }

    public long createDailySubmission(long userId, int time, String evidenceUrl) throws UserNotFoundException {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new UserNotFoundException();
        }
        Daily currentDaily = getCurrentDaily();
        DailySubmission submission = dailySubmissionRepository
                .save(new DailySubmission(time, evidenceUrl, currentDaily, user.get()));

        long hh = submission.getTime() / 3600;
        long mm = (submission.getTime() % 3600) / 60;
        long ss = submission.getTime() % 60;

        MessageCreateActionRowComponent actionRow = new MessageCreateActionRowComponent()
                .addComponent(new MessageCreateButtonComponent(3, "Verify", String.format("verify_%s", submission.getId())))
                .addComponent(new MessageCreateButtonComponent(4, "Reject", String.format("reject_%s", submission.getId())));
        MessageCreatePayload payload = new MessageCreatePayload(String.format("<@&%s>", submissionModeratorRoleId))
                .addEmbed(
                        new MessageCreateEmbed(
                                "New Run Submitted",
                                String.format(
                                        "**Run ID:** `%s`\n**Time taken:** `%02d:%02d:%02d`\n**Submitted By:** `%s`\n[Evidence URL](%s)",
                                        submission.getId(), hh, mm, ss, submission.getSubmittedBy().toString(), submission.getEvidenceUrl()
                                )
                        )
                )
                .addComponent(actionRow);

        RestTemplate restTemplate = new RestTemplate();
        var messageCreateRequest = RequestEntity
                .post(String.format(createMessageEndpoint, submissionChannelId))
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bot " + botToken)
                .body(payload);
        restTemplate.exchange(messageCreateRequest, Void.class);

        return submission.getId();
    }

    public Optional<DailySubmission> getDailySubmission(long runId) {
        return dailySubmissionRepository.findById(runId);
    }
}
