package ru.leroy.screenerapi.service;

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
    void userRegistration_failWithThrowEmailException() {
        given(this.repository.findByEmail(this.user.getEmail()))
            .willReturn(Optional.of(this.user));
        assertThatThrownBy(() -> this.underTest.registration(this.user))
            .isInstanceOf(EmailExistException.class)
            .hasMessageContaining(new EmailExistException().getMessage());
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
            .willReturn(Optional.of(this.user));
        this.user.setPassword("foo");
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
}
