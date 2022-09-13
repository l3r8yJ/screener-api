package ru.leroy.screenerapi.exception.user;

/**
 * The type Same password exception.
 */
public class SamePasswordException extends RuntimeException {
  public SamePasswordException() {
    super("Entered password matches new one");
  }
}
