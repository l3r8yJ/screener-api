package ru.leroy.screenerapi.util;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.Test;

class UsersUtilTest {
  @Test
  void randomRate_work() {
    assertThat(UsersUtil.randomRate())
        .containsPattern("free|pro");
  }
}