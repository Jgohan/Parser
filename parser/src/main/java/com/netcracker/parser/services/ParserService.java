package com.netcracker.parser.services;

import com.netcracker.parser.entities.Template;

public interface ParserService {
    Template identifyTemplate(String string);

    void parseString(String string);

    void saveTemplate(Template template);

    void saveUpdatingTemplate(Template template);

    void deleteTemplate(Template template);
}
