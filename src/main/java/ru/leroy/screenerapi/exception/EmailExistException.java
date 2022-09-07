package ru.leroy.screenerapi.exception;

public class EmailExistException extends RuntimeException {
    public EmailExistException() {
        super("This email already exist!");
    }
}
