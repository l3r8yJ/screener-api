package ru.leroy.screenerapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.leroy.screenerapi.entity.UserEntity;
import ru.leroy.screenerapi.exception.AuthenticationException;
import ru.leroy.screenerapi.exception.EmailExistException;
import ru.leroy.screenerapi.exception.EmailNotFoundException;
import ru.leroy.screenerapi.exception.UserNotFoundException;
import ru.leroy.screenerapi.message.ResponseMessages;
import ru.leroy.screenerapi.service.UserService;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService service;

    public UserController(final UserService service) {
        this.service = service;
    }

    @PostMapping("/registration")
    public ResponseEntity<?> registration(@Valid @RequestBody final UserEntity user) {
        try {
            final UserEntity registered = this.service.registration(user);
            return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(registered);
        } catch (final EmailExistException ex) {
            return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ex.getMessage());
        } catch (final Exception ex) {
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ResponseMessages.UNEXPECTED_ERROR);
        }
    }

    @PostMapping("/authentication")
    public ResponseEntity<?> authentication(@Valid @RequestBody final UserEntity user) {
        try {
            final UserEntity auth = this.service.authentication(user);
            return ResponseEntity
                .status(HttpStatus.OK)
                .body(auth);
        } catch (final EmailNotFoundException ex) {
            return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
        } catch (final AuthenticationException ex) {
            return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(ex.getMessage());
        } catch (final Exception ex) {
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ResponseMessages.UNEXPECTED_ERROR);
        }
    }

    @PutMapping("/password/change/{id}")
    public ResponseEntity<?> updatePasswordById(@Valid @PathVariable final Long id, @RequestBody final UserEntity user) {
        try {
            this.service.updateUserPasswordById(id, user.getPassword());
            return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(ResponseMessages.PASSWORD_UPDATED);
        } catch (final UserNotFoundException ex) {
            return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
        } catch (final Exception ex) {
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ResponseMessages.UNEXPECTED_ERROR);
        }
    }

    @PutMapping("/rate/change/{id}")
    public ResponseEntity<?> updateRateById(@Valid @PathVariable final Long id, @RequestBody final UserEntity user) {
        try {
            this.service.updateRateById(id, user.getRate());
            return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(ResponseMessages.RATE_UPDATED);
        } catch (final UserNotFoundException ex) {
            return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
        } catch (final Exception ex) {
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ResponseMessages.UNEXPECTED_ERROR);
        }
    }

    @GetMapping("/info/{id}")
    public ResponseEntity<?> userById(@Valid @PathVariable final Long id) {
        try {
            final UserEntity user = this.service.userById(id);
            return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(user);
        } catch (final UserNotFoundException ex) {
            return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
        } catch (final Exception ex) {
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ResponseMessages.UNEXPECTED_ERROR);
        }
    }
}
