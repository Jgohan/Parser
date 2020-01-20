package com.netcracker.parser.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class TemplateWithThisTemplateStringAlreadyExistsException extends RuntimeException {
    private static String message = "This template string is already used";

    public TemplateWithThisTemplateStringAlreadyExistsException() {
        super(message);
    }
}
