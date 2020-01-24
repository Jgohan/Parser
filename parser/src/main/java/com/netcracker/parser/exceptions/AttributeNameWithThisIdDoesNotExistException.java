package com.netcracker.parser.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class AttributeNameWithThisIdDoesNotExistException extends RuntimeException{
    private static String message = "This attribute name doesn\'t exists";

    public AttributeNameWithThisIdDoesNotExistException() {
        super(message);
    }
}
