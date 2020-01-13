package com.netcracker.parser.controllers;

import com.netcracker.parser.entities.Template;
import com.netcracker.parser.services.implementations.TemplateServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping("/template")
public class TemplateController {
    private final TemplateServiceImpl templateService;
    private static final Logger logger = LoggerFactory.getLogger(TemplateController.class);

    public TemplateController(TemplateServiceImpl templateService) {
        this.templateService = templateService;
    }


    @GetMapping
    public Iterable<Template> getTemplates() {
        return templateService.getAllTemplates();
    }

    @PostMapping
    public void addTemplate(@RequestBody Template template) {
        logger.info("Adding Template: " + template.getTemplateName() + ":  " +
                template.getTemplateString());

        templateService.saveTemplate(template);
    }

    @PutMapping
    public void updateTemplate(@RequestBody Template template) {
        logger.info("Updating Template " + template.getId() + "). " +
                template.getTemplateName() + ":  " + template.getTemplateString());

        templateService.saveUpdatingTemplate(template);
    }

    @DeleteMapping
    public void deleteTemplate(@RequestBody String id) {
        logger.info("Deleting template with id = " + id);

        templateService.deleteTemplate(Long.parseLong(id));
    }
}
