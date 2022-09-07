package ru.leroy.screenerapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.*;
import ru.leroy.screenerapi.entity.UserEntity;
import ru.leroy.screenerapi.exception.EmailExistException;
import ru.leroy.screenerapi.exception.UserNotFoundException;
import ru.leroy.screenerapi.message.ResponseMessages;
import ru.leroy.screenerapi.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping("/registration")
    public ResponseEntity<?> registration(@RequestBody final UserEntity user) {
        try {
            UserEntity registered = service.registration(user);
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

    @GetMapping("/info/{id}")
    public ResponseEntity<?> userById(@PathVariable final Long id) {
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
