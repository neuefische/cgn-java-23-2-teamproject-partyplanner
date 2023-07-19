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
    public String handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        return exception.getAllErrors().get(0).getDefaultMessage();
    }

    @ExceptionHandler({NoSuchPartyException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNoSuchPartyException(NoSuchPartyException exception) {
        return exception.getMessage();
    }

    @ExceptionHandler({UsernameAlreadyExistsException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String handleUsernameAlreadyExistsException(UsernameAlreadyExistsException exception) {
        return exception.getMessage();
    }
}
