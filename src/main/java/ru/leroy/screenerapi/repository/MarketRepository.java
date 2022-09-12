package ru.leroy.screenerapi.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.leroy.screenerapi.entity.MarketEntity;

/**
 * The interface Market repository.
 */

@Repository
public interface MarketRepository extends CrudRepository<MarketEntity, Long> {

}
