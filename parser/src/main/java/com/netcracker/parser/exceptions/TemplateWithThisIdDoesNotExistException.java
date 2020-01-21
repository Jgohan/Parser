package com.netcracker.parser.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class TemplateWithThisIdDoesNotExistException extends RuntimeException {
    private static String message = "This template does\'t exist";

    public TemplateWithThisIdDoesNotExistException() {
        super(message);
    }
}
