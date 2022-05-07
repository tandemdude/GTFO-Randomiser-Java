package io.github.tandemdude.gtforandomiser.repositories;

import io.github.tandemdude.gtforandomiser.models.db.Daily;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DailyRepository extends CrudRepository<Daily, String> {
}
