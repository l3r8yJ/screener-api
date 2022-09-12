package ru.leroy.screenerapi.controller;

import javax.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.leroy.screenerapi.dto.user.UserResponseDto;
import ru.leroy.screenerapi.dto.user.registration.UserRequestRegistration;
import ru.leroy.screenerapi.entity.UserEntity;
import ru.leroy.screenerapi.exception.AuthenticationException;
import ru.leroy.screenerapi.exception.EmailExistException;
import ru.leroy.screenerapi.exception.EmailNotFoundException;
import ru.leroy.screenerapi.exception.UserNotFoundException;
import ru.leroy.screenerapi.message.ResponseMessages;
import ru.leroy.screenerapi.service.UserService;

/**
 * The type User controller.
 */
@RestController
@RequestMapping("/user")
public class UserController {
  private final UserService service;

  private final ModelMapper modelMapper = new ModelMapper();

  public UserController(final UserService service) {
    this.service = service;
  }

  /**
   * Registration method.
   *
   * @param request Json with user data.
   * @return response with user field
  */
  @PostMapping("/registration")
  public ResponseEntity<?> registration(@RequestBody final UserRequestRegistration request) {
    final UserEntity entity = this.modelMapper.map(request, UserEntity.class);
    try {
      final UserEntity registered = this.service.registration(entity);
      final UserResponseDto userResponseRegistration =
          this.modelMapper.map(registered, UserResponseDto.class);
      return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(userResponseRegistration);
    } catch (final EmailExistException ex) {
      return ResponseEntity
        .status(HttpStatus.CONFLICT)
        .body(ex.getMessage());
    } catch (final Exception ex) {
      return badRequestResponse();
    }
  }

  /**
   * Authentication method.
   *
   * @param user Json with data
   * @return response with user data
  */
  @PostMapping("/authentication")
  public ResponseEntity<?> authentication(@Valid @RequestBody final UserEntity user) {
    try {
      final UserEntity auth = this.service.authentication(user);
      return ResponseEntity
        .status(HttpStatus.OK)
        .body(auth);
    } catch (final EmailNotFoundException ex) {
      return notFoundResponse(ex);
    } catch (final AuthenticationException ex) {
      return ResponseEntity
        .status(HttpStatus.UNAUTHORIZED)
        .body(ex.getMessage());
    } catch (final Exception ex) {
      return badRequestResponse();
    }
  }

  /**
   * Change password method.
   *
   * @param id Of the user
   * @param user user with new password
   * @return updated user
  */
  @PutMapping("/password/change/{id}")
  public ResponseEntity<String> updatePasswordById(
      @Valid @PathVariable final Long id, @RequestBody final UserEntity user) {
    try {
      this.service.updateUserPasswordById(id, user.getPassword());
      return ResponseEntity
        .status(HttpStatus.ACCEPTED)
        .body(ResponseMessages.PASSWORD_UPDATED);
    } catch (final UserNotFoundException ex) {
      return notFoundResponse(ex);
    } catch (final Exception ex) {
      return badRequestResponse();
    }
  }

  /**
   * Update rate method.
   *
   * @param id of user
   * @param user user with new rate
   * @return updated user
  */
  @PutMapping("/rate/change/{id}")
  public ResponseEntity<String> updateRateById(
      @Valid @PathVariable final Long id, @RequestBody final UserEntity user) {
    try {
      this.service.updateRateById(id, user.getRate());
      return ResponseEntity
        .status(HttpStatus.ACCEPTED)
        .body(ResponseMessages.RATE_UPDATED);
    } catch (final UserNotFoundException ex) {
      return notFoundResponse(ex);
    } catch (final Exception ex) {
      return badRequestResponse();
    }
  }

  /**
   * Info about user by id method.
   *
   * @param id of user
   * @return user as json
  */
  @GetMapping("/info/{id}")
  public ResponseEntity<?> userById(@Valid @PathVariable final Long id) {
    try {
      final UserEntity user = this.service.userById(id);
      return ResponseEntity
        .status(HttpStatus.ACCEPTED)
        .body(user);
    } catch (final UserNotFoundException ex) {
      return notFoundResponse(ex);
    } catch (final Exception ex) {
      return badRequestResponse();
    }
  }

  private static ResponseEntity<String> notFoundResponse(final Exception ex) {
    return ResponseEntity
        .status(HttpStatus.NOT_FOUND)
        .body(ex.getMessage());
  }

  private static ResponseEntity<String> badRequestResponse() {
    return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(ResponseMessages.UNEXPECTED_ERROR);
  }
}
