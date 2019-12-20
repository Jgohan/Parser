package com.netcracker.parser.controllers;

import com.netcracker.parser.entities.Template;
import com.netcracker.parser.services.ParserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class ParserController {
    @Autowired
    private ParserServiceImpl parserService;

    @PostMapping("/parser")
    public void parseString(@RequestBody String string) {
        System.out.println(string);

        parserService.parseString(string.trim());
    }

    @PostMapping("/template")
    public void addTemplate(@RequestBody Template template) {
        System.out.println("ADD " + template.getTemplateName() + ":  " +
                template.getTemplateString());

        parserService.saveTemplate(template);
    }

    @PutMapping("/template")
    public void updateTemplate(@RequestBody Template template) {
        System.out.println("UPDATE " + template.getId() + "). " +
                template.getTemplateName() + ":  " + template.getTemplateString());

        parserService.saveUpdatingTemplate(template);
    }

    @DeleteMapping("template")
    public void deleteTemplate(@RequestBody Template template) {
        System.out.println("DELETE template with id = " + template.getId());

        parserService.deleteTemplate(template);
    }
}
