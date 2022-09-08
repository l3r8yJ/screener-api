package ru.leroy.screenerapi.util;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class UsersUtilTest {
    @Test
    void randomRate_work() {
        assertThat(UsersUtil.randomRate())
            .containsPattern("free|pro");
    }
}