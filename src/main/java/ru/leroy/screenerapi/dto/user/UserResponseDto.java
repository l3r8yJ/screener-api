package ru.leroy.screenerapi.dto.user;

import java.time.Instant;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.Data;

/**
 * The type User registration response dto.
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
