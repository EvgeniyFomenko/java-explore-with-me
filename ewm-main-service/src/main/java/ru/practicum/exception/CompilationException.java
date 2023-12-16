package ru.practicum.exception;

public class CompilationException extends RuntimeException {
    public CompilationException(String messageError) {
        super(messageError);
    }
}
