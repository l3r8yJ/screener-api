package ru.leroy.screenerapi.exception;

/**
 * The type Email exist exception.
 */
public class EmailExistException extends RuntimeException {
  private final String email;

  public EmailExistException(final String email) {
    super(String.format("Email: %s already exist!", email));
    this.email = email;
  }

  @Override
  public String getMessage() {
    return String.format("Email: %s already exist!", this.email);
  }
}
