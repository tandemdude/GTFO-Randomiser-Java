package io.github.tandemdude.gtforandomiser.models.responses;

import lombok.Data;

@Data
public class DailySubmissionResponse {
    private final long id;
    private final int time;
    private final String evidenceUrl;
    private final String submittedFor;
    private final long submittedBy;
}
