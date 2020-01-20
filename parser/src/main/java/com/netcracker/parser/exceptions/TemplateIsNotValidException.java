package com.netcracker.parser.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class TemplateIsNotValidException extends RuntimeException {
    private static String message = "Template string is not valid";

    public TemplateIsNotValidException() {
        super(message);
    }
}
