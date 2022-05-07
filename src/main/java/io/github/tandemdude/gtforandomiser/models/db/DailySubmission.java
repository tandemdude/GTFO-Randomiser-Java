package io.github.tandemdude.gtforandomiser.models.db;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "daily_submissions", schema = "randomiser")
public class DailySubmission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private int time;
    private String evidenceUrl;
    private boolean verified = false;

    @ManyToOne
    private Daily submittedFor;
    @ManyToOne
    private User submittedBy;

    protected DailySubmission() {}

    public DailySubmission(int time, String evidenceUrl, Daily submittedFor, User submittedBy) {
        this.time = time;
        this.evidenceUrl = evidenceUrl;
        this.submittedFor = submittedFor;
        this.submittedBy = submittedBy;
    }

    public String getFormattedTime() {
        long hh = time / 3600;
        long mm = (time % 3600) / 60;
        long ss = time % 60;
        return String.format("%02d:%02d:%02d", hh, mm, ss);
    }

    public String toString() {
        return String.format(
                "DailySubmission[%s, %s, %s, %s#%s]",
                id, submittedFor.getId(), getFormattedTime(),
                submittedBy.getUsername(), submittedBy.getDiscriminator()
        );
    }
}
