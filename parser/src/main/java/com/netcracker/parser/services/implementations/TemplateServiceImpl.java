package com.netcracker.parser.services.implementations;

import com.netcracker.parser.entities.Template;
import com.netcracker.parser.exceptions.TemplateIsNotValidException;
import com.netcracker.parser.exceptions.TemplateStringWasNotIdentifiedException;
import com.netcracker.parser.exceptions.TemplateWithThisIdDoesNotExistException;
import com.netcracker.parser.repositories.TemplateRepository;
import org.springframework.stereotype.Service;

@Service
public class TemplateServiceImpl {
    private final TemplateRepository templateRepository;

    public TemplateServiceImpl(TemplateRepository templateRepository) {
        this.templateRepository = templateRepository;
    }


    public Iterable<Template> getAllTemplates() {
        return templateRepository.findAll();
    }

    public void saveTemplate(Template template) {
        if (template.countAttributes() < 1) throw new TemplateIsNotValidException();
        templateRepository.save(template);
    }

    public void saveUpdatingTemplate(Template template) {
        Template updatingTemplate = templateRepository.findById(template.getId())
                .orElseThrow(TemplateWithThisIdDoesNotExistException::new);

        if (template.countAttributes() == updatingTemplate.countAttributes()) {
            updatingTemplate.setTemplateString(template.getTemplateString());
            if(template.getTemplateName().isEmpty()) throw new TemplateIsNotValidException();
            updatingTemplate.setTemplateName(template.getTemplateName());

            templateRepository.save(updatingTemplate);
        } else {
            throw new TemplateStringWasNotIdentifiedException();
        }
    }

    public void deleteTemplate(Long id) {
        Template deletingTemplate = templateRepository.findById(id)
                .orElseThrow(TemplateWithThisIdDoesNotExistException::new);

        templateRepository.delete(deletingTemplate);
    }
}
