package io.github.tandemdude.gtforandomiser.controllers;

import io.github.tandemdude.gtforandomiser.models.db.User;
import io.github.tandemdude.gtforandomiser.models.exceptions.AuthenticationFailedException;
import io.github.tandemdude.gtforandomiser.models.responses.DiscordOauthUserResponse;
import io.github.tandemdude.gtforandomiser.repositories.UserRepository;
import io.github.tandemdude.gtforandomiser.services.DiscordOauthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/oauth/discord")
public class DiscordOauthController {
    private final DiscordOauthService oauthService;
    private final UserRepository userRepository;

    public DiscordOauthController(DiscordOauthService oauthService, UserRepository userRepository) {
        this.oauthService = oauthService;
        this.userRepository = userRepository;
    }

    @GetMapping("/authRedirect")
    public ResponseEntity<Void> redirectToDiscord(HttpSession session) {
        String state = oauthService.generateState();
        session.setAttribute("state", state);
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(oauthService.generateRedirectUrl(session))
                .build();
    }

    @GetMapping("/completeAuth")
    public ModelAndView completeDiscordOauth(@RequestParam String state, @RequestParam(required = false) String code, HttpSession session) {
        var storedState = session.getAttribute("state");
        if (storedState == null || code == null || !storedState.toString().equals(state)) {
            session.removeAttribute("state");
            session.setAttribute("message", "Login failed, please try again.");
            return new ModelAndView("redirect:/");
        }

        try {
            DiscordOauthUserResponse user = oauthService.completeAuthentication(code);
            session.setAttribute("userId", user.getId());
            userRepository.save(new User(user.getId(), user.getUsername(), user.getDiscriminator()));
        } catch (AuthenticationFailedException e) {
            session.setAttribute("message", "Login failed, please try again.");
            return new ModelAndView("redirect:/");
        }

        return new ModelAndView("redirect:/profile");
    }
}
