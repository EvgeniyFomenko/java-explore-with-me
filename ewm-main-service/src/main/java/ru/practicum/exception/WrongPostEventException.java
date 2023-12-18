package ru.practicum.exception;

public class WrongPostEventException extends RuntimeException {
    public WrongPostEventException(String s) {
        super(s);
    }
}
