package ru.leroy.screenerapi.service;

import org.springframework.stereotype.Service;
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
}
