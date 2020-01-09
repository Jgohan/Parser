package com.netcracker.parser.controllers;

import com.netcracker.parser.entities.Template;
import com.netcracker.parser.services.implementations.TemplateServiceImpl;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping("/template")
public class TemplateController {
    private final TemplateServiceImpl templateService;

    public TemplateController(TemplateServiceImpl templateService) {
        this.templateService = templateService;
    }


    @GetMapping
    public Iterable<Template> getTemplates() {
        System.out.println("LOAD");

        return templateService.getAllTemplates();
    }

    @PostMapping
    public void addTemplate(@RequestBody Template template) {
        System.out.println("ADD " + template.getTemplateName() + ":  " +
                template.getTemplateString());

        templateService.saveTemplate(template);
    }

    @PutMapping
    public void updateTemplate(@RequestBody Template template) {
        System.out.println("UPDATE " + template.getId() + "). " +
                template.getTemplateName() + ":  " + template.getTemplateString());

        templateService.saveUpdatingTemplate(template);
    }

    @DeleteMapping
    public void deleteTemplate(@RequestBody String id) {
        System.out.println("DELETE template with id = " + id);

        templateService.deleteTemplate(Long.parseLong(id));
    }
}
