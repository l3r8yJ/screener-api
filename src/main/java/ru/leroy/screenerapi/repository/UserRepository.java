package ru.leroy.screenerapi.repository;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.leroy.screenerapi.entity.UserEntity;


/**
 * The interface User repository.
 */
@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {
  Optional<UserEntity> findByEmail(String email);
}
