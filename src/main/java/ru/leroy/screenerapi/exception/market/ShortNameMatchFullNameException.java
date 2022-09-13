package ru.leroy.screenerapi.exception.market;

/**
 * The short name is the same as the full name exception.
 */
public class ShortNameMatchFullNameException extends RuntimeException{
  public ShortNameMatchFullNameException() {
    super("The short name is the same as the full name!");
  }
}
