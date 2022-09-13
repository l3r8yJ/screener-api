package ru.leroy.screenerapi.service;

import org.springframework.stereotype.Service;
import ru.leroy.screenerapi.entity.MarketEntity;
import ru.leroy.screenerapi.exception.market.FullNameExistException;
import ru.leroy.screenerapi.exception.market.ShortNameExistException;
import ru.leroy.screenerapi.exception.market.ShortNameMatchFullNameException;
import ru.leroy.screenerapi.repository.MarketRepository;

/**
 * MarketService with business-logic.
 */
@Service
public class MarketService {
  private final MarketRepository repository;

  public MarketService(final MarketRepository repository) {
    this.repository = repository;
  }

  public MarketEntity create (final MarketEntity market) {
    this.repository
        .findByFullName(market.getFullName())
        .ifPresentOrElse(
            (mrk) -> {
              throw new FullNameExistException(market.getFullName());
            }, () -> {
              this.repository
                  .findByShortName(market.getShortName())
                  .ifPresentOrElse(
                      (mrk) -> {
                        throw new ShortNameExistException(market.getShortName());
                      }, () -> {
                        if(market.getFullName().equals(market.getShortName())) {
                          throw new ShortNameMatchFullNameException();
                        }
                      }
                  );
            }
        );
    return this.repository.save(market);
  }

}
