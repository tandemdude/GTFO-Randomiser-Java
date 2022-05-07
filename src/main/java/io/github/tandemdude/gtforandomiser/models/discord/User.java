package io.github.tandemdude.gtforandomiser.models.discord;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
    private long id;
    private String username;
    private String discriminator;
    private String avatar;
    private boolean bot;
    private boolean system;
    private boolean mfa_enabled;
    private String banner;
    private int accent_color;
    private String locale;
    private int flags;
    private int premium_type;
    private int public_flags;

    @Override
    public String toString() {
        return String.format("%s#%s", username, discriminator);
    }
}
