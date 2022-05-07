package io.github.tandemdude.gtforandomiser.controllers;

import io.github.tandemdude.gtforandomiser.models.responses.*;
import io.github.tandemdude.gtforandomiser.services.LoadoutGeneratorService;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/rundown")
public class RundownController {
    private final LoadoutGeneratorService loadoutGenerator;

    public RundownController(LoadoutGeneratorService loadoutGenerator) {
        this.loadoutGenerator = loadoutGenerator;
    }

    // Rundown ID provided
    @GetMapping("/{rundownId:[1-9][0-9]*}/loadout")
    public LoadoutResponse generateRandomLoadout(@PathVariable int rundownId) {
        return new LoadoutResponse(loadoutGenerator.getRandomLoadout(rundownId));
    }

    @GetMapping("/{rundownId:[1-9][0-9]*}/fullLoadout")
    public FullLoadoutResponse generateRandomFullLoadout(@PathVariable int rundownId) {
        return new FullLoadoutResponse(loadoutGenerator.getRandomFullLoadout(rundownId));
    }

    @GetMapping("/{rundownId:[1-9][0-9]*}/stage")
    public StageResponse generateRandomStage(@PathVariable int rundownId) {
        return new StageResponse(loadoutGenerator.getRandomStageAndDifficulty(rundownId));
    }

    @GetMapping("/{rundownId:[1-9][0-9]*}/fullLoadoutWithStage")
    public FullLoadoutWithStageResponse generateRandomFullLoadoutWithStage(@PathVariable int rundownId) {
        return new FullLoadoutWithStageResponse(
                loadoutGenerator.getRandomFullLoadout(rundownId),
                loadoutGenerator.getRandomStageAndDifficulty(rundownId));
    }

    // No rundown ID provided - use current rundown ID
    @GetMapping("/current/loadout")
    public LoadoutResponse generateRandomLoadout() {
        return generateRandomLoadout(loadoutGenerator.getCurrentRundownId());
    }

    @GetMapping("/current/fullLoadout")
    public FullLoadoutResponse generateRandomFullLoadout() {
        return generateRandomFullLoadout(loadoutGenerator.getCurrentRundownId());
    }

    @GetMapping("/current/stage")
    public StageResponse generateRandomStage() {
        return generateRandomStage(loadoutGenerator.getCurrentRundownId());
    }

    @GetMapping("/current/fullLoadoutWithStage")
    public FullLoadoutWithStageResponse generateRandomFullLoadoutWithStage() {
        return generateRandomFullLoadoutWithStage(loadoutGenerator.getCurrentRundownId());
    }

    // Misc
    @GetMapping("/handicap")
    public HandicapResponse generateRandomHandicap() {
        return new HandicapResponse(loadoutGenerator.getRandomHandicap());
    }
}
