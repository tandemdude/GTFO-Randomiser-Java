package io.github.tandemdude.gtforandomiser.models.discord;

import lombok.Data;

@Data
public class MessageCreateButtonComponent {
    private final int type = 2;
    private final int style;
    private final String label;
    private final String custom_id;
}
