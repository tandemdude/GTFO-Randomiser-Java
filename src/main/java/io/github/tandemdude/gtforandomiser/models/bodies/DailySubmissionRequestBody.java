package io.github.tandemdude.gtforandomiser.models.bodies;

import lombok.Data;

@Data
public class DailySubmissionRequestBody {
    private final long userId;
    private final int hours;
    private final int minutes;
    private final int seconds;
    private final String evidenceUrl;
}
