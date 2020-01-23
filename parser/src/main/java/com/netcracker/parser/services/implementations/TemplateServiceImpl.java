package com.netcracker.parser.services.implementations;

import com.netcracker.parser.entities.Template;
import com.netcracker.parser.exceptions.TemplateIsNotValidException;
import com.netcracker.parser.exceptions.TemplateStringWasNotIdentifiedException;
import com.netcracker.parser.exceptions.TemplateWithThisIdDoesNotExistException;
import com.netcracker.parser.exceptions.TemplateWithThisTemplateStringAlreadyExistsException;
import com.netcracker.parser.repositories.TemplateRepository;
import com.netcracker.parser.security.Response;
import com.netcracker.parser.services.TemplateService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import static com.netcracker.parser.services.Constants.ATTRIBUTE;

@Service
public class TemplateServiceImpl implements TemplateService {
    private final TemplateRepository templateRepository;

    public TemplateServiceImpl(TemplateRepository templateRepository) {
        this.templateRepository = templateRepository;
    }


    @Override
    public Iterable<Template> getAllTemplates() {
        return templateRepository.findAll();
    }

    private boolean isTemplateInvalid(Template template) {
        if (template.getTemplateName().isEmpty()) return true;

        String templateString = template.getTemplateString();
        String[] substrings = templateString.split(ATTRIBUTE, -1);

        if (template.countAttributes() == 0) return true;
        if (substrings.length > 1) {
            for (int i = 1; i < substrings.length - 1; i++) {
                if(!substrings[i].startsWith(" ") || !substrings[i].endsWith(" "))
                    return true;
            }
        }
        if (!templateString.endsWith(ATTRIBUTE) && !substrings[substrings.length - 1].startsWith(" "))
            return true;

        return false;
    }

    @Override
    public ResponseEntity<?> saveTemplate(Template template) {
        try {
            if (isTemplateInvalid(template)) throw new TemplateIsNotValidException();
            if (templateRepository.existsByTemplateString(template.getTemplateString()))
                throw new TemplateWithThisTemplateStringAlreadyExistsException();
            templateRepository.save(template);
            return ResponseEntity.ok(
                    new Response("Template has been added")
            );
        } catch (TemplateIsNotValidException | TemplateWithThisTemplateStringAlreadyExistsException e) {
            return new ResponseEntity<>(
                    new Response(e.getMessage()),
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    @Override
    public ResponseEntity<?> saveUpdatingTemplate(Template template) {
        try {
            Template updatingTemplate = templateRepository.findById(template.getId())
                    .orElseThrow(TemplateWithThisIdDoesNotExistException::new);

            if (template.countAttributes() != updatingTemplate.countAttributes())
                throw new TemplateStringWasNotIdentifiedException();
            else {
                if (isTemplateInvalid(template)) throw new TemplateIsNotValidException();
                    updatingTemplate.setTemplateName(template.getTemplateName());
                    updatingTemplate.setTemplateString(template.getTemplateString());
                    templateRepository.save(updatingTemplate);
                    return ResponseEntity.ok(
                            new Response("Template has been updated")
                    );
            }
        } catch (TemplateWithThisIdDoesNotExistException
                | TemplateIsNotValidException
                | TemplateStringWasNotIdentifiedException e) {
            return new ResponseEntity<>(
                    new Response(e.getMessage()),
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    @Override
    public ResponseEntity<?> deleteTemplate(Long id) {
        try {
            Template deletingTemplate = templateRepository.findById(id)
                    .orElseThrow(TemplateWithThisIdDoesNotExistException::new);
            templateRepository.delete(deletingTemplate);
            return ResponseEntity.ok(
                    new Response("Template has been deleted")
            );
        } catch (TemplateWithThisIdDoesNotExistException e) {
            return new ResponseEntity<>(
                    new Response(e.getMessage()),
                    HttpStatus.BAD_REQUEST
            );
        }
    }
}
