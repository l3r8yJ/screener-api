package ru.leroy.screenerapi.exception;

public class AuthenticationException extends RuntimeException {
    public AuthenticationException() {
        super("Wrong email or password!");
    }
}
