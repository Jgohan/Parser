package com.netcracker.parser.services;

import com.netcracker.parser.entities.Template;
import org.springframework.http.ResponseEntity;

public interface TemplateService {

    Iterable<Template> getAllTemplates();

    ResponseEntity<?> saveTemplate(Template template);

    ResponseEntity<?> saveUpdatingTemplate(Template template);

    ResponseEntity<?> deleteTemplate(Long id);
}
