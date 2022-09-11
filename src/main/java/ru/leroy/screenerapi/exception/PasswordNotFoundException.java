package ru.leroy.screenerapi.exception;

/**
 * The type Password not found exception.
 */
public class PasswordNotFoundException extends RuntimeException {
  public PasswordNotFoundException() {
    super("The user doesn't have a password");
  }
}
