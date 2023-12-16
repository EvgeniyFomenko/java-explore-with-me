package ru.practicum.exception;

public class WrongPostTimeEventException extends RuntimeException {
    public WrongPostTimeEventException(String s) {
        super(s);
    }
}
