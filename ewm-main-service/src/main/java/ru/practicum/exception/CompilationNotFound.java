package ru.practicum.exception;

public class CompilationNotFound extends RuntimeException {
    public CompilationNotFound(String message) {
        super(message);
    }
}
