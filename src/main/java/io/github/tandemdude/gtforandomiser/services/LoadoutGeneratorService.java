package io.github.tandemdude.gtforandomiser.services;

import io.github.tandemdude.gtforandomiser.config.RundownConfig;
import io.github.tandemdude.gtforandomiser.models.data.FullLoadout;
import io.github.tandemdude.gtforandomiser.models.data.Loadout;
import io.github.tandemdude.gtforandomiser.models.data.Stage;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.Set;

@Data
@Service
public class LoadoutGeneratorService {
    private final RundownConfig rundownConfig;
    private final Random random = new Random();

    public LoadoutGeneratorService(RundownConfig rundownConfig) {
        this.rundownConfig = rundownConfig;
    }

    public int getCurrentRundownId() {
        return rundownConfig.getCurrentRundownId();
    }

    public String getRandomPrimary(int rundownId) {
        List<String> primaries = rundownConfig
                .getWeapons()
                .get(rundownId)
                .get("p");
        return primaries.get(random.nextInt(primaries.size()));
    }

    public String getRandomSecondary(int rundownId) {
        List<String> secondaries = rundownConfig
                .getWeapons()
                .get(rundownId)
                .get("s");
        return secondaries.get(random.nextInt(secondaries.size()));
    }

    public String getRandomTool(int rundownId) {
        List<String> tools = rundownConfig
                .getWeapons()
                .get(rundownId)
                .get("t");
        return tools.get(random.nextInt(tools.size()));
    }

    public String getRandomMelee(int rundownId) {
        List<String> melees = rundownConfig
                .getWeapons()
                .get(rundownId)
                .get("m");
        return melees.get(random.nextInt(melees.size()));
    }

    public Loadout getRandomLoadout(int rundownId) {
        return new Loadout(
                getRandomPrimary(rundownId),
                getRandomSecondary(rundownId),
                getRandomTool(rundownId),
                getRandomMelee(rundownId)
        );
    }

    public FullLoadout getRandomFullLoadout(int rundownId) {
        return new FullLoadout(
                getRandomLoadout(rundownId),
                getRandomLoadout(rundownId),
                getRandomLoadout(rundownId),
                getRandomLoadout(rundownId)
        );
    }

    public Stage getRandomStageAndDifficulty(int rundownId) {
        Set<String> availableStages = rundownConfig
                .getStages()
                .get(rundownId)
                .keySet();
        String stageName = availableStages
                .stream()
                .skip(random.nextInt(availableStages.size()))
                .findFirst()
                .orElseThrow();
        List<String> difficulties = rundownConfig
                .getStages()
                .get(rundownId)
                .get(stageName);
        return new Stage(stageName, difficulties.get(random.nextInt(difficulties.size())));
    }

    public String getRandomHandicap() {
        List<String> handicaps = rundownConfig.getHandicaps();
        return handicaps.get(random.nextInt(handicaps.size()));
    }
}
