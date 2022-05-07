package io.github.tandemdude.gtforandomiser.models.responses;

import lombok.Data;

@Data
public class DiscordComponentInteractionResponse {
    private final int type;
    private final DiscordComponentInteractionResponseData data;
}
