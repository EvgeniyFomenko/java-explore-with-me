package ru.practicum.exception;

public class CannotRequestException extends RuntimeException {
    public CannotRequestException(String message) {
        super(message);
    }
}
