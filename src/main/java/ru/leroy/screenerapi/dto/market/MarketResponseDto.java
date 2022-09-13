package ru.leroy.screenerapi.dto.market;

import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import lombok.Data;

/**
 * The type Market response dto.
 */
@Data
public class MarketResponseDto {

  @Id
  private Long id;

  @NotBlank
  private String fullName;

  @NotBlank
  private String shortName;

  @NotBlank
  private String description;

  @NotBlank
  private String picture;
}
