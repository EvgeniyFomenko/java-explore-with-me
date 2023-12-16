package ru.practicum.exception;

public class UserEmailException extends RuntimeException {
    public UserEmailException(String message) {
        super(message);
    }
}
