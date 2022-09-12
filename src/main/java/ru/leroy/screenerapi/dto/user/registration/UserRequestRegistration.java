package ru.leroy.screenerapi.dto.user.registration;

import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
* Entity which we have to use in registration.
*/
@Data
public class UserRequestRegistration {

  @Id
  private Long id;
  @Email
  private String email;
  @NotBlank
  private String password;

  private String rate = "free";

  public void setPassword(final String password) {
    this.password = new BCryptPasswordEncoder().encode(password);
  }
}
