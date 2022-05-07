package io.github.tandemdude.gtforandomiser.controllers;

import io.github.tandemdude.gtforandomiser.models.bodies.DailySubmissionRequestBody;
import io.github.tandemdude.gtforandomiser.models.db.Daily;
import io.github.tandemdude.gtforandomiser.models.db.DailySubmission;
import io.github.tandemdude.gtforandomiser.models.exceptions.UserNotFoundException;
import io.github.tandemdude.gtforandomiser.models.responses.DailySubmissionResponse;
import io.github.tandemdude.gtforandomiser.models.responses.FullLoadoutWithStageResponse;
import io.github.tandemdude.gtforandomiser.services.DailyService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/daily")
public class DailyController {
    private final DailyService dailyService;

    public DailyController(DailyService dailyService) {
        this.dailyService = dailyService;
    }

    @GetMapping("/current")
    public FullLoadoutWithStageResponse getCurrentDaily() {
        Daily currentDaily = dailyService.getCurrentDaily();
        return new FullLoadoutWithStageResponse(currentDaily.getLoadout(), currentDaily.getStage());
    }

    @GetMapping("/{date:[0-9]{4}(?:-[0-9]{2}){2}}")
    public ResponseEntity<?> getSpecificDaily(@PathVariable String date) {
        Optional<Daily> retrievedDaily = dailyService.getDailyForDateString(date);
        if (retrievedDaily.isEmpty()) {
            return ResponseEntity
                    .status(404)
                    .build();
        }
        Daily daily = retrievedDaily.get();
        return ResponseEntity
                .ok(new FullLoadoutWithStageResponse(daily.getLoadout(), daily.getStage()));
    }

    @GetMapping("/submission/{runId:[1-9][0-9]*}")
    public ResponseEntity<?> getDailySubmission(@PathVariable long runId) {
        Optional<DailySubmission> retrievedSubmission = dailyService.getDailySubmission(runId);
        if (retrievedSubmission.isEmpty()) {
            return ResponseEntity
                    .status(404)
                    .build();
        }
        DailySubmission submission = retrievedSubmission.get();
        return ResponseEntity
                .ok(new DailySubmissionResponse(
                        submission.getId(),
                        submission.getTime(),
                        submission.getEvidenceUrl(),
                        submission.getSubmittedFor().getId(),
                        submission.getSubmittedBy().getId()));
    }

    @PostMapping(
            value = "/submission",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_FORM_URLENCODED_VALUE}
    )
    public ResponseEntity<Void> submitDailyRun(@ModelAttribute DailySubmissionRequestBody payload) {
        int time = payload.getSeconds() + (payload.getMinutes() * 60) + (payload.getHours() * 3600);
        try {
            long runId = dailyService
                    .createDailySubmission(payload.getUserId(), time, payload.getEvidenceUrl());
            return ResponseEntity
                    .created(URI.create(String.format("/api/v1/daily/submission/%s", runId)))
                    .build();
        } catch (UserNotFoundException e) {
            return ResponseEntity
                    .status(401)
                    .build();
        }
    }
}
