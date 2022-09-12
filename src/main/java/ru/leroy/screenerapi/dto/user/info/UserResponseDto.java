package ru.leroy.screenerapi.dto.user.info;

import java.time.Instant;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.Data;

/**
* Full info DTO.
*/
@Data
public class UserResponseDto {

  @Id
  private Long id;

  @Email
  private String email;

  @NotBlank
  private String password;

  @NotBlank
  private String rate;

  private Instant expiration;
}
