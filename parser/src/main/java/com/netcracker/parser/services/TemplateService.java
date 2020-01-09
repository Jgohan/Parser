package com.netcracker.parser.services;

import com.netcracker.parser.entities.Template;

public interface TemplateService {
    Iterable<Template> getAllTemplates();

    void saveTemplate(Template template);

    void saveUpdatingTemplate(Template template);

    void deleteTemplate(Long id);
}
