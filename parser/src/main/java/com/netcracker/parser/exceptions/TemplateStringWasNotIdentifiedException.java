package com.netcracker.parser.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class TemplateStringWasNotIdentifiedException extends RuntimeException {
    private static String message = "Template string was not identified";

    public TemplateStringWasNotIdentifiedException() {
        super(message);
    }
}
