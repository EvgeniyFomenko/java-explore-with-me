package ru.practicum.exception;

public class NotFoundCategoryException extends RuntimeException {
    public NotFoundCategoryException(String message) {
        super(message);
    }
}
