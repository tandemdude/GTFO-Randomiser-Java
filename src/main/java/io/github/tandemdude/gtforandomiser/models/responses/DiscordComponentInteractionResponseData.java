package io.github.tandemdude.gtforandomiser.models.responses;

import lombok.Data;

import java.util.Collections;
import java.util.List;

@Data
public class DiscordComponentInteractionResponseData {
    private final String content;
    private final List<?> components = Collections.emptyList();
}
