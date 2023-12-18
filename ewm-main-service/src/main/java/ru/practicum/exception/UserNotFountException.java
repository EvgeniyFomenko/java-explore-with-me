package ru.practicum.exception;

public class UserNotFountException extends RuntimeException {
    public UserNotFountException(String message) {
        super(message);
    }
}
