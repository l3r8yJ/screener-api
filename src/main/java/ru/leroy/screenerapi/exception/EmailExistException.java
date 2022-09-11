package ru.leroy.screenerapi.exception;

/**
 * The type Email exist exception.
 */
public class EmailExistException extends RuntimeException {
  public EmailExistException(final String email) {
    super(String.format("This email: %s already exist!", email));
  }
}
