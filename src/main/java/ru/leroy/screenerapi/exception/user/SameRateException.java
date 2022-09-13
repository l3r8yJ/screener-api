package ru.leroy.screenerapi.exception.user;

/**
 * The type Same rate exception.
 */
public class SameRateException extends RuntimeException {
  public SameRateException() {
    super("Entered rate matches new one");
  }
}
