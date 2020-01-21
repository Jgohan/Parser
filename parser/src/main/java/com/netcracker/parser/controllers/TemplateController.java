package com.netcracker.parser.controllers;

import com.netcracker.parser.entities.Template;
import com.netcracker.parser.services.TemplateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping("/template")
public class TemplateController {
    private final TemplateService templateService;
    private static final Logger logger = LoggerFactory.getLogger(TemplateController.class);

    public TemplateController(TemplateService templateService) {
        this.templateService = templateService;
    }


    @GetMapping
    public Iterable<Template> getTemplates() {
        logger.info("Returning Template list");

        return templateService.getAllTemplates();
    }

    @PostMapping
    public ResponseEntity<?> addTemplate(@RequestBody Template template) {
        logger.info("Adding Template:\n" + template.getTemplateName() + " - " +
                template.getTemplateString());

        return templateService.saveTemplate(template);
    }

    @PutMapping
    public ResponseEntity<?> updateTemplate(@RequestBody Template template) {
        logger.info("Updating Template with id " + template.getId() + ":\n" +
                template.getTemplateName() + " - " + template.getTemplateString());

        return templateService.saveUpdatingTemplate(template);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteTemplate(@RequestBody String id) {
        logger.info("Deleting template with id " + id);

        return templateService.deleteTemplate(Long.parseLong(id));
    }
}
