package ru.leroy.screenerapi.repository;

import net.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import ru.leroy.screenerapi.entity.UserEntity;
import ru.leroy.screenerapi.exception.UserNotFoundException;
import ru.leroy.screenerapi.util.Names;

import java.time.Instant;
import java.util.Random;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class UserRepositoryTest {

    @Autowired
    private UserRepository underTest;

    private UserEntity usr;

    @BeforeEach
    void setUp() {
        usr = new UserEntity();
        usr.setId(new Random().nextLong());
        usr.setRate(Names.FREE_RATE);
        usr.setEmail(RandomString.make().concat("@gmail.com"));
        usr.setPassword(RandomString.make().concat("-pass-from-test"));
        usr.setExpiration(Instant.now());
        underTest.save(usr);
    }

    @Test
    void shouldToGiveUserByMail_fail() {
        assertThrows(
            UserNotFoundException.class,
            () -> underTest.findByEmail(Names.FOO).orElseThrow(UserNotFoundException::new)
        );
    }

    @Test
    void shouldToGiveUserByMail_success() {
        assertThat(usr.getEmail())
            .isEqualTo(underTest.findByEmail(usr.getEmail()).orElseThrow(UserNotFoundException::new).getEmail());
    }

    @Test
    void shouldFindUserById_fail() {
        assertThat(underTest.findById(new Random().nextLong()))
            .isNotPresent();
    }

    @Test
    void shouldFindUserById_success() {
        UserEntity exist = underTest.findByEmail(usr.getEmail()).orElseThrow();
        assertThat(underTest.findById(exist.getId())).isPresent();
    }

    @Test
    void shouldExistUserById_fail() {
        assertThat(underTest.existsById(new Random().nextLong()))
            .isFalse();
    }

    @Test
    void shouldExistUserById_success() {
        UserEntity exist = underTest.findByEmail(usr.getEmail()).orElseThrow();
        assertThat(underTest.existsById(exist.getId()))
            .isTrue();
    }
}