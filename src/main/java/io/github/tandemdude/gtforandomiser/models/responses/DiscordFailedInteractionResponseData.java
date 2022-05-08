package io.github.tandemdude.gtforandomiser.models.responses;

import lombok.Data;

@Data
public class DiscordFailedInteractionResponseData {
    private final String content;
    private final int flags = 1 << 6;
}
