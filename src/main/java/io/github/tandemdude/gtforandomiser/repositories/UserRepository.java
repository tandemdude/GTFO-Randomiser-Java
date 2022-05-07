package io.github.tandemdude.gtforandomiser.repositories;

import io.github.tandemdude.gtforandomiser.models.db.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findById(long id);
}
