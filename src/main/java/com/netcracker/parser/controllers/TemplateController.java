package com.netcracker.parser.controllers;

import com.netcracker.parser.entities.Template;
import com.netcracker.parser.services.TemplateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping("/template")
@RequiredArgsConstructor
@Log4j2
public class TemplateController {

    private final TemplateService templateService;


    @GetMapping
    public Iterable<Template> getTemplates() {
        log.info("Returning Template list");

        return templateService.getAllTemplates();
    }

    @PostMapping
    public ResponseEntity<?> addTemplate(@RequestBody Template template) {
        log.info("Adding Template: {} - {}", template.getTemplateName(), template.getTemplateString());

        return templateService.saveTemplate(template);
    }

    @PutMapping
    public ResponseEntity<?> updateTemplate(@RequestBody Template template) {
        log.info("Updating Template with id {}: {} - {}", template.getId(), template.getTemplateName(), template.getTemplateString());

        return templateService.saveUpdatingTemplate(template);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteTemplate(@RequestBody String id) {
        log.info("Deleting template with id {}", id);

        return templateService.deleteTemplate(Long.parseLong(id));
    }
}
