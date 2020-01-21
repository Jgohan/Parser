package com.netcracker.parser.services;

import com.netcracker.parser.entities.Template;
import com.netcracker.parser.entities.User;
import org.springframework.http.ResponseEntity;

public interface ParserService {
    Template identifyTemplate(String string);

    ResponseEntity<?> parseString(String string, User author);
}
