package ru.leroy.screenerapi.exception;

public class SamePasswordException extends RuntimeException {
    public SamePasswordException() {
        super("Entered password matches new one");
    }
}
