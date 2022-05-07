package io.github.tandemdude.gtforandomiser.models.discord;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Interaction {
    private long id;
    private long application_id;
    private int type;
    private InteractionData data;
    private long guild_id;
    private long channel_id;
    private Member member;
    private User user;
    private String token;
    private int version;

    public User getAuthor() {
        return user != null ? user : member.getUser();
    }
}
