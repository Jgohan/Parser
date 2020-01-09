package com.netcracker.parser.services;

import com.netcracker.parser.entities.Template;

public interface ParserService {
    Template identifyTemplate(String string);

    void parseString(String string);
}
