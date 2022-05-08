package io.github.tandemdude.gtforandomiser.models.responses;

import lombok.Data;

@Data
public class DiscordInteractionResponse<T> {
    private final int type;
    private final T data;
}
