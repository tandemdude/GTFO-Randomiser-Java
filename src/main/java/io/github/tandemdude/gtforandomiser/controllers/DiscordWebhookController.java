package io.github.tandemdude.gtforandomiser.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import io.github.tandemdude.gtforandomiser.models.discord.Interaction;
import io.github.tandemdude.gtforandomiser.models.responses.DiscordPingInteractionResponse;
import io.github.tandemdude.gtforandomiser.services.DiscordWebhookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/discord")
public class DiscordWebhookController {
    private static final JsonMapper MAPPER = new JsonMapper();
    private final DiscordWebhookService webhookService;

    public DiscordWebhookController(DiscordWebhookService webhookService) {
        this.webhookService = webhookService;
    }

    @PostMapping("/consumeWebhook")
    public ResponseEntity<?> consumeDiscordInteractionWebhook(
            @RequestHeader(value = "X-Signature-Ed25519", required = false) String signature,
            @RequestHeader(value = "X-Signature-Timestamp", required = false) String timestamp,
            @RequestBody String rawPayload
    ) {
        if (signature == null || timestamp == null || !webhookService.validateWebhookSignature(rawPayload, signature, timestamp)) {
            return ResponseEntity.status(401).build();
        }

        Interaction payload;
        try {
            payload = MAPPER.readValue(rawPayload, Interaction.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }

        if (payload.getType() == 1) {
            return ResponseEntity
                    .ok(new DiscordPingInteractionResponse(1));
        }

        return ResponseEntity
                .ok(webhookService.handleInteraction(payload));
    }
}
