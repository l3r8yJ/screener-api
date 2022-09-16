package ru.leroy.screenerapi.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.util.Optional;
import lombok.NonNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;
import ru.leroy.screenerapi.entity.UserEntity;
import ru.leroy.screenerapi.exception.AuthenticationException;
import ru.leroy.screenerapi.exception.EmailExistException;
import ru.leroy.screenerapi.exception.EmailNotFoundException;
import ru.leroy.screenerapi.exception.InvalidPasswordException;
import ru.leroy.screenerapi.exception.PasswordNotExistException;
import ru.leroy.screenerapi.exception.SamePasswordException;
import ru.leroy.screenerapi.exception.SameRateException;
import ru.leroy.screenerapi.repository.UserRepository;
import ru.leroy.screenerapi.util.UsersUtil;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class UserServiceTest {

  @Mock
  private UserRepository repository;

  private UserService underTest;

  private UserEntity user;

  private static UserEntity withNewPassword(@NonNull final UserEntity user, final String pass) {
    final UserEntity copy = new UserEntity();
    copy.setId(user.getId());
    copy.setEmail(user.getEmail());
    copy.setPassword(pass);
    copy.setRate(user.getRate());
    copy.setExpiration(user.getExpiration());
    return copy;
  }

  @BeforeEach
  void setUp() {
    this.underTest = new UserService(this.repository);
    this.user = new UsersUtil().buildRandomUser();
  }

  @Test
  void canGetAllUsers() {
    this.underTest.index();
    verify(this.repository).findAll();
  }

  @Test
  void userRegistrationSuccess() {
    this.underTest.registration(this.user);
    final ArgumentCaptor<UserEntity> captor = ArgumentCaptor.forClass(UserEntity.class);
    verify(this.repository)
        .save(captor.capture());
    final UserEntity captured = captor.getValue();
    assertThat(captured)
        .isEqualTo(this.user);
  }

  @Test
  void userRegistrationFailWithThrowEmailExistException() {
    given(this.repository.findByEmail(this.user.getEmail()))
        .willReturn(Optional.of(this.user));
    assertThatThrownBy(() -> this.underTest.registration(this.user))
        .isInstanceOf(EmailExistException.class)
        .hasMessageContaining(new EmailExistException(this.user.getEmail()).getMessage());
    verify(this.repository, never()).save(any());
  }

  @Test
  void userRegistrationFailWithThrowPasswordNotExistException() {
    final UserEntity expected = withNewPassword(this.user, "");
    assertThatThrownBy(() -> this.underTest.registration(expected))
        .isInstanceOf(PasswordNotExistException.class)
        .hasMessageContaining(new PasswordNotExistException().getMessage());
    verify(this.repository, never()).save(any());
  }

  @Test
  void userRegistrationFailWithThrowInvalidPasswordException() {
    final UserEntity expected = withNewPassword(this.user, "invalid password");
    assertThatThrownBy(() -> this.underTest.registration(expected))
        .isInstanceOf(InvalidPasswordException.class)
        .hasMessageContaining(new InvalidPasswordException().getMessage());
    verify(this.repository, never()).save(any());
  }

  @Test
  void userAuthenticationSuccess() {
    given(this.repository.findByEmail(this.user.getEmail()))
        .willReturn(Optional.of(this.user));
    assertThat(this.underTest.authentication(this.user))
        .isInstanceOf(UserEntity.class)
        .isEqualTo(this.user);
    verify(this.repository)
        .findByEmail(this.user.getEmail());
  }

  @Test
  void userAuthenticationThrowAuthenticationException() {
    given(this.repository.findByEmail(this.user.getEmail()))
        .willReturn(Optional.of(withNewPassword(this.user, "wrong password")));
    assertThatThrownBy(() -> this.underTest.authentication(this.user))
        .isInstanceOf(AuthenticationException.class)
        .hasMessageContaining(new AuthenticationException().getMessage());
  }

  @Test
  void userAuthenticationThrowEmailNotFoundException() {
    assertThatThrownBy(() -> this.underTest.authentication(this.user))
        .isInstanceOf(EmailNotFoundException.class)
        .hasMessageContaining(new EmailNotFoundException(this.user.getEmail()).getMessage());
  }

  @Test
  void updatePasswordByIdSuccess() {
    given(this.repository.findById(this.user.getId()))
        .willReturn(Optional.of(this.user));
    final UserEntity actual =
        this.underTest.updateUserPasswordById(this.user.getId(), "new password");
    final UserEntity expected = withNewPassword(actual, "new password");
    assertThat(actual)
        .isNotNull()
        .isInstanceOf(expected.getClass())
        .isEqualTo(expected);
    assertThat(actual.getPassword()).isEqualTo(expected.getPassword());
  }

  @Test
  void updatePasswordByIdFailWithSamePasswordExceptionThrown() {
    given(this.repository.findById(this.user.getId())).willReturn(Optional.of(this.user));
    assertThatThrownBy(
        () -> this.underTest.updateUserPasswordById(this.user.getId(), this.user.getPassword())
    )
        .isInstanceOf(SamePasswordException.class)
        .hasMessageContaining(new SamePasswordException().getMessage());
    verify(this.repository, never()).save(any());
  }

  @Test
  void updateRateByIdSuccess() {
    this.user.setRate("pro");
    given(this.repository.findById(this.user.getId()))
        .willReturn(Optional.of(this.user));
    final UserEntity actual = this.underTest.updateRateById(this.user.getId(), "free");
    this.user.setRate("free");
    assertThat(actual)
        .isNotNull()
        .isInstanceOf(this.user.getClass())
        .isEqualTo(this.user);
    assertThat(actual.getRate()).isEqualTo(this.user.getRate());
  }

  @Test
  void updateRateByIdFailWithSameRateException() {
    given(this.repository.findById(this.user.getId()))
        .willReturn(Optional.of(this.user));
    this.user.setRate("free");
    assertThatThrownBy(() -> this.underTest.updateRateById(this.user.getId(), "free"))
        .isInstanceOf(SameRateException.class)
        .hasMessageContaining(new SameRateException().getMessage());
    verify(this.repository, never()).save(any());
  }
}
