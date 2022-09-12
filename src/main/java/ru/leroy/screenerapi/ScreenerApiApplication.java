package ru.leroy.screenerapi;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
* Rest API starter.
*/
@SpringBootApplication
public class ScreenerApiApplication {

  @Bean
  public ModelMapper modelMapper() {
    return new ModelMapper();
  }

  public static void main(final String[] args) {
    SpringApplication.run(ScreenerApiApplication.class, args);
  }
}
