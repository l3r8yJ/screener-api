package ru.leroy.screenerapi.exception;

public class SameRateException extends RuntimeException {
    public SameRateException() {
        super("Entered rate matches new one");
    }
}
