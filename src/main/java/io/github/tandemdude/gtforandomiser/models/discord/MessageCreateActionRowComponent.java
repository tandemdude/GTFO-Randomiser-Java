package io.github.tandemdude.gtforandomiser.models.discord;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MessageCreateActionRowComponent {
    private final int type = 1;
    private final List<MessageCreateButtonComponent> components = new ArrayList<>();

    public MessageCreateActionRowComponent addComponent(MessageCreateButtonComponent component) {
        components.add(component);
        return this;
    }
}
