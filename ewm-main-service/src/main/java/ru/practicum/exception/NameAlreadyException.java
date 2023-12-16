package ru.practicum.exception;

public class NameAlreadyException extends RuntimeException {
    public NameAlreadyException(String message) {
        super(message);
    }
}
