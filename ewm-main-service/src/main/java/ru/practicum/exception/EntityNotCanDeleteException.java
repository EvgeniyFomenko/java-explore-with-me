package ru.practicum.exception;

public class EntityNotCanDeleteException extends RuntimeException {
    public EntityNotCanDeleteException(String message) {
        super(message);
    }
}
