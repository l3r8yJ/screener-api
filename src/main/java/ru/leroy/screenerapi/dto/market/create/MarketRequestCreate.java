package ru.leroy.screenerapi.dto.market.create;

import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

/**
 * Entity which we have to use in create.
 */
public class MarketRequestCreate {
  @Id
  private Long id;

  @NotBlank
  private String fullName;

  @NotBlank
  private  String shortName;

  @NotBlank
  private String description;

  @NotBlank
  private String picture;
}
