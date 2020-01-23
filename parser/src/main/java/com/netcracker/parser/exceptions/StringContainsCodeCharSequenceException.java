package com.netcracker.parser.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static com.netcracker.parser.services.Constants.ATTRIBUTE;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class StringContainsCodeCharSequenceException extends RuntimeException {
    private static String message = "String contains code char sequence: " + ATTRIBUTE;

    public StringContainsCodeCharSequenceException() {
        super(message);
    }
}
