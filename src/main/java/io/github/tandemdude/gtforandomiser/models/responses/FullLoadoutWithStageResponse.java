package io.github.tandemdude.gtforandomiser.models.responses;

import io.github.tandemdude.gtforandomiser.models.data.FullLoadout;
import io.github.tandemdude.gtforandomiser.models.data.Stage;
import lombok.Data;

@Data
public class FullLoadoutWithStageResponse {
    private final FullLoadout loadouts;
    private final Stage stage;
}
