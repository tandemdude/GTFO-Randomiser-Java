package io.github.tandemdude.gtforandomiser.models.discord;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MessageCreatePayload {
    private final String content;
    private final List<MessageCreateEmbed> embeds = new ArrayList<>();
    private final List<MessageCreateActionRowComponent> components = new ArrayList<>();

    public MessageCreatePayload addEmbed(MessageCreateEmbed embed) {
        embeds.add(embed);
        return this;
    }

    public MessageCreatePayload addComponent(MessageCreateActionRowComponent component) {
        components.add(component);
        return this;
    }
}
