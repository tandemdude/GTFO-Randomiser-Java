package io.github.tandemdude.gtforandomiser.repositories;

import io.github.tandemdude.gtforandomiser.models.db.Daily;
import io.github.tandemdude.gtforandomiser.models.db.DailySubmission;
import io.github.tandemdude.gtforandomiser.models.db.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DailySubmissionRepository extends CrudRepository<DailySubmission, Long> {
    Optional<DailySubmission> findById(long id);
    List<DailySubmission> findAllBySubmittedByOrderBySubmittedForDesc(User submittedBy);
    List<DailySubmission> findAllBySubmittedForOrderByTimeAsc(Daily submittedFor);
    Optional<DailySubmission> findFirstBySubmittedForAndVerifiedTrueOrderByTimeAsc(Daily submittedFor);
}
