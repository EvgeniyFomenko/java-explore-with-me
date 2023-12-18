package ru.practicum.exception;

public class CannotPublishedException extends RuntimeException {
    public CannotPublishedException(String message) {
        super(message);
    }
}
