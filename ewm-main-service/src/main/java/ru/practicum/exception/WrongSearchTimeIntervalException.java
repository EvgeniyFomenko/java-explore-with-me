package ru.practicum.exception;

public class WrongSearchTimeIntervalException extends RuntimeException {
    public WrongSearchTimeIntervalException(String message) {
        super(message);
    }
}
