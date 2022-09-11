package ru.leroy.screenerapi.service;

import java.util.Objects;
import org.springframework.stereotype.Service;
import ru.leroy.screenerapi.entity.UserEntity;
import ru.leroy.screenerapi.exception.AuthenticationException;
import ru.leroy.screenerapi.exception.EmailExistException;
import ru.leroy.screenerapi.exception.EmailNotFoundException;
import ru.leroy.screenerapi.exception.InvalidPasswordException;
import ru.leroy.screenerapi.exception.PasswordNotExistException;
import ru.leroy.screenerapi.exception.SamePasswordException;
import ru.leroy.screenerapi.exception.SameRateException;
import ru.leroy.screenerapi.exception.UserNotFoundException;
import ru.leroy.screenerapi.message.RateNames;
import ru.leroy.screenerapi.repository.UserRepository;

/**
* UserService with business-logic.
*/
@Service
public class UserService {
  private final UserRepository repository;

  public UserService(final UserRepository repository) {
    this.repository = repository;
  }

  public Iterable<UserEntity> index() {
    return this.repository.findAll();
  }

  public UserEntity userById(final Long id) throws UserNotFoundException {
    return this.userBy(id);
  }

  /**
   * User authentication method.
   *
   * @param auth user with a login and password as json
   * @return user entity as json with full data from table
   * @throws AuthenticationException when wrong credentials
   * @throws EmailNotFoundException when email not found
  */
  public UserEntity authentication(final UserEntity auth)
      throws AuthenticationException, EmailNotFoundException {
    final UserEntity user = this.repository
        .findByEmail(auth.getEmail())
        .orElseThrow(() -> new EmailNotFoundException(auth.getEmail()));
    if (!Objects.equals(user.getPassword(), auth.getPassword())) {
      throw new AuthenticationException();
    }
    return user;
  }

  /**
   * Registration user.
   *
   * @param user as json with by email and password
   * @return new user
   * @throws EmailExistException when entered email exist
   * @throws PasswordNotExistException when password not exist
   * @throws InvalidPasswordException when password is not valid
  */
  public UserEntity registration(final UserEntity user) throws EmailExistException {
    user.setRate(RateNames.FREE_RATE);
    this.repository
        .findByEmail(user.getEmail())
        .ifPresentOrElse(
            (usr) -> {
              throw new EmailExistException(user.getEmail());
            },
            () -> this.passwordValidation(user.getPassword())
        );
    return this.repository.save(user);
  }

  /**
   * Update user pass by an id.
   *
   * @param id of the user
   * @param pass of the user
   * @return updated user as json
   * @throws UserNotFoundException when user not found by id
   * @throws SamePasswordException when new password matches new one
  */
  public UserEntity updateUserPasswordById(final Long id, final String pass)
      throws UserNotFoundException, SamePasswordException {
    final UserEntity updated = this.userBy(id);
    if (Objects.equals(pass, updated.getPassword())) {
      throw new SamePasswordException();
    }
    updated.setPassword(pass);
    return updated;
  }

  /**
   * Update user rate by an id.
   *
   * @param id of the user
   * @param rate of the user
   * @return updated user as json
   * @throws UserNotFoundException when user not found
  */
  public UserEntity updateRateById(final Long id, final String rate) throws UserNotFoundException {
    final UserEntity updated = this.userBy(id);
    if (Objects.equals(rate, updated.getRate())) {
      throw new SameRateException();
    }
    updated.setRate(rate);
    return updated;
  }

  private UserEntity userBy(final Long id) throws UserNotFoundException {
    return this.repository
        .findById(id)
        .orElseThrow(UserNotFoundException::new);
  }

  private void passwordValidation(final String password)
      throws InvalidPasswordException, PasswordNotExistException {
    /*
      At least 1 number, 1 lowercase letter, 1 uppercase letter; no spaces in the entire string;
      at least 8 characters, no more than 40 characters.
    */
    final String passwordPattern = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,40}";
    if (Objects.isNull(password)) {
      throw new PasswordNotExistException();
    }
    if (!password.matches(passwordPattern)) {
      throw new InvalidPasswordException();
    }
  }
}
