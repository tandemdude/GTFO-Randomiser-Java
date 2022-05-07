package io.github.tandemdude.gtforandomiser.models.responses;

import lombok.Data;

@Data
public class DiscordOauthUserResponse {
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
}
