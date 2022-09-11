package ru.leroy.screenerapi.exception;

/**
 * The type Invalid password exception.
 */
public class InvalidPasswordException extends RuntimeException {
  public InvalidPasswordException() {
    super("The current password is not valid");
  }
}
