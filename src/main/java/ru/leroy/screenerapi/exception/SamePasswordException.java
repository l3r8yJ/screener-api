package ru.leroy.screenerapi.exception;

/**
 * The type Same password exception.
 */
public class SamePasswordException extends RuntimeException {
  public SamePasswordException() {
    super("Entered password matches new one");
  }
}
