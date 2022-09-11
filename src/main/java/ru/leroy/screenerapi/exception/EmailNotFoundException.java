package ru.leroy.screenerapi.exception;

/**
 * The type Email not found exception.
 */
public class EmailNotFoundException extends RuntimeException {
  public EmailNotFoundException(final String email) {
    super(String.format("User with: %s not found", email));
  }
}
