package ru.leroy.screenerapi.dto;

import javax.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
* Entity which we have to use in registration.
*/
@Data
public class UserRegistrationDto {

  @NotBlank
  public String email;
  @NotBlank
  public String password;

  public void setPassword(final String password) {
    this.password = new BCryptPasswordEncoder().encode(password);
  }
}
