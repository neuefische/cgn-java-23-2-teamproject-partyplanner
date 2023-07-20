package org.partypets.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        return new ErrorMessage(exception.getAllErrors().get(0).getDefaultMessage());
    }

    @ExceptionHandler({NoSuchPartyException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorMessage handleNoSuchPartyException(NoSuchPartyException exception) {
        return new ErrorMessage(exception.getMessage());
    }

    @ExceptionHandler({UsernameAlreadyExistsException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorMessage handleUsernameAlreadyExistsException(UsernameAlreadyExistsException exception) {
        return new ErrorMessage(exception.getMessage());
    }
}
