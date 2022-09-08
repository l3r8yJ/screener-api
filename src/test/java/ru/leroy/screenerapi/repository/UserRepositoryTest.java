package ru.leroy.screenerapi.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import ru.leroy.screenerapi.entity.UserEntity;
import ru.leroy.screenerapi.exception.UserNotFoundException;
import ru.leroy.screenerapi.util.UsersUtil;

import java.util.Random;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@ActiveProfiles("test")
class UserRepositoryTest {

    @Autowired
    private UserRepository underTest;

    private UserEntity user;

    @BeforeEach
    void setUp() {
        this.user = new UsersUtil().buildRandomUser();
        this.underTest.save(this.user);
    }

    @Test
    void shouldToGiveUserByMail_fail() {
        assertThrows(
            UserNotFoundException.class,
            () -> this.underTest.findByEmail("foo").orElseThrow(UserNotFoundException::new)
        );
    }

    @Test
    void shouldToGiveUserByMail_success() {
        assertThat(this.user.getEmail())
            .isEqualTo(this.underTest.findByEmail(this.user.getEmail()).orElseThrow(UserNotFoundException::new).getEmail());
    }

    @Test
    void shouldFindUserById_fail() {
        assertThat(this.underTest.findById(new Random().nextLong()))
            .isNotPresent();
    }

    @Test
    void shouldFindUserById_success() {
        final UserEntity exist = this.underTest.findByEmail(this.user.getEmail()).orElseThrow();
        assertThat(this.underTest.findById(exist.getId())).isPresent();
    }

    @Test
    void shouldExistUserById_fail() {
        assertThat(this.underTest.existsById(new Random().nextLong()))
            .isFalse();
    }

    @Test
    void shouldExistUserById_success() {
        final UserEntity exist = this.underTest.findByEmail(this.user.getEmail()).orElseThrow();
        assertThat(this.underTest.existsById(exist.getId()))
            .isTrue();
    }
}