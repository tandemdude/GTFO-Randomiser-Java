package io.github.tandemdude.gtforandomiser.models.discord;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Member {
    private User user;
    private String nick;
    private String avatar;
    private List<Long> roles;
    private String joined_at;
    private String premium_since;
    private boolean deaf;
    private boolean pending;
    private String permissions;
    private String communication_disabled_until;
}
