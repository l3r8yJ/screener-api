package ru.leroy.screenerapi.dto;

import java.time.Instant;
import lombok.Data;

/**
 * The type User registration response dto.
 */
@Data
public class UserRegistrationResponseDto {
  private Long id;
  private String email;
  private String password;
  private String rate;
  private Instant expiration;
}
