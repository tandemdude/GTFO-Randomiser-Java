package io.github.tandemdude.gtforandomiser.controllers;

import io.github.tandemdude.gtforandomiser.models.db.Daily;
import io.github.tandemdude.gtforandomiser.models.db.DailySubmission;
import io.github.tandemdude.gtforandomiser.models.db.User;
import io.github.tandemdude.gtforandomiser.repositories.DailySubmissionRepository;
import io.github.tandemdude.gtforandomiser.repositories.UserRepository;
import io.github.tandemdude.gtforandomiser.services.DailyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Controller
public class FrontendController {
    private final DailyService dailyService;
    private final UserRepository userRepository;
    private final DailySubmissionRepository submissionRepository;

    public FrontendController(DailyService dailyService, UserRepository userRepository, DailySubmissionRepository submissionRepository) {
        this.dailyService = dailyService;
        this.userRepository = userRepository;
        this.submissionRepository = submissionRepository;
    }

    @GetMapping("/")
    public String viewIndex(Model viewModel, HttpSession session) {
        Object userId = session.getAttribute("userId");
        viewModel.addAttribute("userId", userId == null ? null : userId.toString());
        Object message = session.getAttribute("message");
        session.removeAttribute("message");
        viewModel.addAttribute("message", message == null ? null : message.toString());
        return "index";
    }

    @GetMapping("/daily")
    public String viewDaily(Model viewModel, HttpSession session) {
        Object userId = session.getAttribute("userId");
        viewModel.addAttribute("userId", userId == null ? null : userId.toString());

        Daily currentDaily = dailyService.getCurrentDaily();
        viewModel.addAttribute("dailyRun", currentDaily);

        List<DailySubmission> submissions = submissionRepository.findAllBySubmittedForOrderByTimeAsc(currentDaily);
        viewModel.addAttribute("submissions", submissions);

        Optional<Daily> yesterdaysDaily = dailyService.getYesterdaysDaily();
        DailySubmission yesterdaysBestSubmission = null;
        if (yesterdaysDaily.isPresent()) {
            Optional<DailySubmission> fetched = submissionRepository
                    .findFirstBySubmittedForAndVerifiedTrueOrderByTimeAsc(yesterdaysDaily.get());
            if (fetched.isPresent()) {
                yesterdaysBestSubmission = fetched.get();
            }
        }
        viewModel.addAttribute("yesterdaysBest", yesterdaysBestSubmission);
        return "daily";
    }

//    @GetMapping("/weekly")
//    public String viewWeekly(Model viewModel, HttpSession session) {
//        return "weekly";
//    }

    @GetMapping("/profile")
    public String viewProfile(Model viewModel, HttpSession session) {
        Object userId = session.getAttribute("userId");
        if (userId == null) {
            session.setAttribute("message", "You must be logged in to view profile page");
            return "redirect:/";
        }
        Optional<User> authedUser = userRepository.findById(Long.parseLong(userId.toString()));
        if (authedUser.isEmpty()) {
            session.setAttribute("message", "You must be logged in to view profile page");
            session.removeAttribute("userId");
            return "redirect:/";
        }
        List<DailySubmission> submissions = submissionRepository
                .findAllBySubmittedByOrderBySubmittedForDesc(authedUser.get());
        viewModel.addAttribute("submissions", submissions);
        viewModel.addAttribute("user", authedUser.get());
        return "profile";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("userId");
        return "logout";
    }
}
