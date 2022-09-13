package ru.leroy.screenerapi.exception.user;

/**
 * The type Authentication exception.
 */
public class AuthenticationException extends RuntimeException {
  public AuthenticationException() {
    super("Wrong email or password!");
  }
}
