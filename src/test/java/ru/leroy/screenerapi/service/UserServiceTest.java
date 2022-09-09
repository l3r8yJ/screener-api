package ru.leroy.screenerapi.service;

import lombok.NonNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import ru.leroy.screenerapi.entity.UserEntity;
import ru.leroy.screenerapi.exception.AuthenticationException;
import ru.leroy.screenerapi.exception.EmailExistException;
import ru.leroy.screenerapi.exception.EmailNotFoundException;
import ru.leroy.screenerapi.exception.SamePasswordException;
import ru.leroy.screenerapi.repository.UserRepository;
import ru.leroy.screenerapi.util.UsersUtil;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class UserServiceTest {

    @Mock
    private UserRepository repository;

    private UserService underTest;

    private UserEntity user;

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
    void userRegistration_success() {
        this.underTest.registration(this.user);
        final ArgumentCaptor<UserEntity> captor = ArgumentCaptor.forClass(UserEntity.class);
        verify(this.repository)
            .save(captor.capture());
        final UserEntity captured = captor.getValue();
        assertThat(captured)
            .isEqualTo(this.user);
    }

    @Test
    void userRegistration_failWithThrowEmailExistException() {
        given(this.repository.findByEmail(this.user.getEmail()))
            .willReturn(Optional.of(this.user));
        assertThatThrownBy(() -> this.underTest.registration(this.user))
            .isInstanceOf(EmailExistException.class)
            .hasMessageContaining(new EmailExistException(this.user.getEmail()).getMessage());
        verify(this.repository, never()).save(any());
    }

    @Test
    void userAuthentication_success() {
        given(this.repository.findByEmail(this.user.getEmail()))
            .willReturn(Optional.of(this.user));
        assertThat(this.underTest.authentication(this.user))
            .isInstanceOf(UserEntity.class)
            .isEqualTo(this.user);
        verify(this.repository)
            .findByEmail(this.user.getEmail());
    }

    @Test
    void userAuthentication_throwAuthenticationException() {
        given(this.repository.findByEmail(this.user.getEmail()))
            .willReturn(Optional.of(withNewPassword(this.user, "wrong password")));
        assertThatThrownBy(() -> this.underTest.authentication(this.user))
            .isInstanceOf(AuthenticationException.class)
            .hasMessageContaining(new AuthenticationException().getMessage());
    }

    @Test
    void userAuthentication_throwEmailNotFoundException() {
        assertThatThrownBy(() -> this.underTest.authentication(this.user))
            .isInstanceOf(EmailNotFoundException.class)
            .hasMessageContaining(new EmailNotFoundException(this.user.getEmail()).getMessage());
    }

    @Test
    @Transactional
    void updatePasswordById_success() {
        given(this.repository.findById(this.user.getId()))
            .willReturn(Optional.of(this.user));
        final UserEntity actual = this.underTest.updateUserPasswordById(this.user.getId(), "new password");
        final UserEntity expected = withNewPassword(actual, "new password");
        assertThat(actual.getPassword()).isEqualTo(expected.getPassword());
    }

    @Test
    void updatePasswordById_failWithSamePasswordExceptionThrown() {
        given(this.repository.findById(this.user.getId())).willReturn(Optional.of(this.user));
        assertThatThrownBy(
            () -> this.underTest.updateUserPasswordById(this.user.getId(), this.user.getPassword())
        )
            .isInstanceOf(SamePasswordException.class)
            .hasMessageContaining(new SamePasswordException().getMessage());
        verify(this.repository, never()).save(any());
    }

    private static UserEntity withNewPassword(@NonNull final UserEntity user, final String pass) {
        final UserEntity copy = new UserEntity();
        copy.setId(user.getId());
        copy.setEmail(user.getEmail());
        copy.setPassword(pass);
        copy.setRate(user.getRate());
        copy.setExpiration(user.getExpiration());
        return copy;
    }
}
