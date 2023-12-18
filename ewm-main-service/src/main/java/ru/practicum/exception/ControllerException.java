package ru.practicum.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestControllerAdvice
public class ControllerException {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError cannotPublished(CannotPublishedException cannotPublishedException) {
        return ApiError.builder().message(cannotPublishedException.getMessage())
                .timestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .status("CONFLICT")
                .reason("For the requested operation the conditions are not met.").build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError wrongPostEvent(WrongPostEventException wrongPostEventException) {
        return ApiError.builder().message(wrongPostEventException.getMessage())
                .timestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .status("BAD_REQUEST")
                .reason("Incorrectly made request.")
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ApiError wrongTimeEvent(WrongPostTimeEventException wrongPostTimeEventException) {
        return ApiError.builder().message(wrongPostTimeEventException.getMessage())
                .timestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .status("FORBIDDEN")
                .reason("For the requested operation the conditions are not met.")
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError eventNotFoundException(NotFoundEventException notFoundEventException) {
        return ApiError.builder().message(notFoundEventException.getMessage())
                .timestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .status("NOT_FOUND")
                .reason("Event not found")
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError dateWrongException(WrongSearchTimeIntervalException wrongSearchTimeIntervalException) {
        return ApiError.builder().message(wrongSearchTimeIntervalException.getMessage())
                .timestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .status(HttpStatus.BAD_REQUEST.toString())
                .reason("Incorrectly made request.")
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError emailWrongException(UserEmailException userEmailException) {
        return ApiError.builder().message(userEmailException.getMessage())
                .timestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .status(HttpStatus.BAD_REQUEST.toString())
                .reason("Incorrectly made request.")
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError categoryWrongException(CategoryException categoryException) {
        return ApiError.builder().message(categoryException.getMessage())
                .timestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .status(HttpStatus.BAD_REQUEST.toString())
                .reason("Incorrectly made request.")
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError compilationWrongException(CompilationException compilationException) {
        return ApiError.builder().message(compilationException.getMessage())
                .timestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .status(HttpStatus.BAD_REQUEST.toString())
                .reason("Incorrectly made request.")
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError compilationNotFoundException(CompilationNotFound compilationNotFound) {
        return ApiError.builder().message(compilationNotFound.getMessage())
                .timestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .status("NOT_FOUND")
                .reason("Compilation not found")
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError nameConflictException(NameAlreadyException nameAlreadyException) {
        return ApiError.builder().message(nameAlreadyException.getMessage())
                .timestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .status(HttpStatus.CONFLICT.toString())
                .reason("Conflict")
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError deleteConflictException(EntityNotCanDeleteException compilationNotFound) {
        return ApiError.builder().message(compilationNotFound.getMessage())
                .timestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .status(HttpStatus.CONFLICT.toString())
                .reason("Conflict")
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError cannotRequestException(CannotRequestException cannotRequestException) {
        return ApiError.builder().message(cannotRequestException.getMessage())
                .timestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .status(HttpStatus.CONFLICT.toString())
                .reason("Conflict")
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError categoryNotFoundException(NotFoundCategoryException notFoundCategoryException) {
        return ApiError.builder().message(notFoundCategoryException.getMessage())
                .timestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .status("NOT_FOUND")
                .reason("Category not found")
                .build();
    }

}
