package ru.leroy.screenerapi.exception.market;

/**
 * The type ShortName exist exception.
 */
public class ShortNameExistException extends RuntimeException {
  private final String shortName;

  public ShortNameExistException (final String shortName) {
    super (String.format("Short name: %s already exist!", shortName));
    this.shortName = shortName;
  }

  @Override
  public String getMessage() {
    return String.format("Short name: %s already exist!", this.shortName);
  }
}
