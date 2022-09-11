package ru.leroy.screenerapi.exception;

/**
 * The type User not found exception.
 */
public class UserNotFoundException extends RuntimeException {
  public UserNotFoundException() {
    super("User not found!");
  }
}
