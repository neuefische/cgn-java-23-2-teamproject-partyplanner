package org.partypets.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Party not found!")
public class NoSuchPartyException extends RuntimeException {
    public NoSuchPartyException() {
    }

    public NoSuchPartyException(String id) {
        super("Party not found: " + id);
    }
}
