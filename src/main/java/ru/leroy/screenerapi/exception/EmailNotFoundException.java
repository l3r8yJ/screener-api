package ru.leroy.screenerapi.exception;

public class EmailNotFoundException extends RuntimeException {
    public EmailNotFoundException(String email) {
        super(String.format("User with: %s not found", email));
    }
}
