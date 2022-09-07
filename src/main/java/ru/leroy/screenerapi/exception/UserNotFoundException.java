package ru.leroy.screenerapi.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super("User not found!");
    }
}
