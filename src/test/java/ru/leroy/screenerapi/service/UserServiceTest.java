package ru.leroy.screenerapi.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import ru.leroy.screenerapi.entity.UserEntity;
import ru.leroy.screenerapi.exception.EmailExistException;
import ru.leroy.screenerapi.util.UsersUtil;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@ActiveProfiles("test")
class UserServiceTest {

    @MockBean
    private UserService underTest;

    private UserEntity user;

    @BeforeEach
    void setUp() {
        this.user = new UsersUtil().buildRandomUser();
    }

    @Test
    void registrationUser_success() {
        assertThat(this.underTest.registration(this.user))
            .isNotNull();
    }

    @Test
    void registrationUser_fail() {
        this.underTest.registration(this.user);
        assertThrows(EmailExistException.class, () -> { this.underTest.registration(this.user); });
    }
}