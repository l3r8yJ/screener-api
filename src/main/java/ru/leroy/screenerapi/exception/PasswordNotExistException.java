package ru.leroy.screenerapi.exception;

/**
 * The type Password not exist exception.
 */
public class PasswordNotExistException extends RuntimeException {
  public PasswordNotExistException() {
    super("The user doesn't have a password");
  }
}
