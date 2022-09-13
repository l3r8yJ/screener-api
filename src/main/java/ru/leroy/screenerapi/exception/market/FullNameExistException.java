package ru.leroy.screenerapi.exception.market;

/**
 * The type FullName exist exception.
 */
public class FullNameExistException  extends RuntimeException {
  private final String fullName;

  public FullNameExistException (final String fullName) {
    super (String.format("Full name: %s already exist!", fullName));
    this.fullName = fullName;
  }

  @Override
  public String getMessage () {
    return String.format("Full name: %s already exist!", this.fullName);
  }
}
