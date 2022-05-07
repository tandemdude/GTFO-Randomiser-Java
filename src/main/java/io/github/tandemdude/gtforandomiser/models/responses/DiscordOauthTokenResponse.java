package io.github.tandemdude.gtforandomiser.models.responses;

import lombok.Data;

@Data
public class DiscordOauthTokenResponse {
    private String access_token;
    private String token_type;
    private int expires_in;
    private String refresh_token;
    private String scope;
}
