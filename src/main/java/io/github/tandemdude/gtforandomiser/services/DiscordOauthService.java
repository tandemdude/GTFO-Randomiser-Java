package io.github.tandemdude.gtforandomiser.services;

import io.github.tandemdude.gtforandomiser.models.exceptions.AuthenticationFailedException;
import io.github.tandemdude.gtforandomiser.models.responses.DiscordOauthTokenResponse;
import io.github.tandemdude.gtforandomiser.models.responses.DiscordOauthUserResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpSession;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Map;
import java.util.UUID;

@Service
public class DiscordOauthService {
    private static final SecureRandom random = new SecureRandom();
    private static final String allowedChars = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String grantUri = "https://discord.com/api/oauth2/authorize";
    private static final String tokenUri = "https://discord.com/api/oauth2/token";
    private static final String revokeUri = "https://discord.com/api/oauth2/token/revoke";
    private static final String userUri = "https://discord.com/api/users/@me";

    @Value("${DISCORD_CLIENT_ID}")
    private String clientId;
    @Value("${DISCORD_CLIENT_SECRET}")
    private String clientSecret;
    @Value("${DISCORD_REDIRECT_URI}")
    private String redirectURI;

    public URI generateRedirectUrl(HttpSession session) {
        String state = generateState();
        session.setAttribute("state", state);
        return UriComponentsBuilder
                .fromUriString(grantUri)
                .queryParam("state", state)
                .queryParam("scope", "identify")
                .queryParam("client_id", clientId)
                .queryParam("response_type", "code")
                .queryParam("redirect_uri", redirectURI)
                .buildAndExpand(Map.of())
                .toUri();
    }

    public String generateState() {
        return UUID.randomUUID().toString();
    }

    public DiscordOauthUserResponse completeAuthentication(String code) throws AuthenticationFailedException {
        Map<String, String> formDataMap = Map.of(
                "code", code,
                "client_secret", clientSecret,
                "client_id", clientId,
                "grant_type", "authorization_code",
                "redirect_uri", redirectURI
        );

        StringBuilder formDataStringBuilder = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, String> entry : formDataMap.entrySet()) {
            if (!first) {
                formDataStringBuilder.append("&");
            }
            formDataStringBuilder
                    .append(URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8))
                    .append("=")
                    .append(URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8));
            first = false;
        }

        // Fetch token
        RestTemplate tokenRestTemplate = new RestTemplate();
        var tokenRequest = RequestEntity
                .post(tokenUri)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(formDataStringBuilder.toString());
        var tokenResponse = tokenRestTemplate
                .exchange(tokenRequest, DiscordOauthTokenResponse.class);
        DiscordOauthTokenResponse tokenResponseBody = tokenResponse.getBody();
        if (tokenResponseBody == null) {
            throw new AuthenticationFailedException();
        }

        // Fetch user object
        RestTemplate userRestTemplate = new RestTemplate();
        var userRequest = RequestEntity
                .get(userUri)
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + tokenResponseBody.getAccess_token())
                .build();
        var userResponse = userRestTemplate
                .exchange(userRequest, DiscordOauthUserResponse.class);
        DiscordOauthUserResponse userResponseBody = userResponse.getBody();
        if (userResponseBody == null) {
            throw new AuthenticationFailedException();
        }
        return userResponseBody;
    }
}
