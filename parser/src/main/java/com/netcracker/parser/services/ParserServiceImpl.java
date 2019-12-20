package com.netcracker.parser.services;

import com.netcracker.parser.entities.Attribute;
import com.netcracker.parser.entities.Message;
import com.netcracker.parser.entities.Template;
import com.netcracker.parser.exceptions.TemplateIsNotValidException;
import com.netcracker.parser.exceptions.TemplateStringWasNotIdentifiedException;
import com.netcracker.parser.exceptions.TemplateWithThisIdDoesNotExistException;
import com.netcracker.parser.repositories.AttributeRepository;
import com.netcracker.parser.repositories.MessageRepository;
import com.netcracker.parser.repositories.TemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class ParserServiceImpl implements ParserService {
    @Autowired
    private TemplateRepository templateRepository;
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private AttributeRepository attributeRepository;

    public Template identifyTemplate(String string) {
        Iterable<Template> templates = templateRepository.findAll();
        int containedSubstringsNumber, attributesNumber;

        for (Template template : templates) {
            String[] templateSubstrings = template.getTemplateString().split("_att_");

            containedSubstringsNumber = 0;
            String stringBuffer = string;
            for (String substring : templateSubstrings) {
                if (stringBuffer.contains(substring)) {
                    containedSubstringsNumber++;

                    if (!substring.isEmpty())
                        stringBuffer = stringBuffer.replaceFirst(substring, " ");
                }
            }

            attributesNumber = stringBuffer.trim().split(" ").length;

            if (
                    attributesNumber == template.countAttributes()
                    && containedSubstringsNumber == templateSubstrings.length
                    && string.split(" ").length == template.getTemplateString().split(" ").length
            ) return template;
        }

        throw new TemplateStringWasNotIdentifiedException();
    }

    public void parseString(String string) {
        Template template = identifyTemplate(string);

        Message message = new Message();
        message.setTemplate(template);

        Attribute[] attributes = new Attribute[template.countAttributes()];
        for (int i = 0; i < attributes.length; i++) {
            attributes[i] = new Attribute(i + 1, message);
        }

        String[] substrings = string.split(" ");
        String[] templateSubstrings = template.getTemplateString().split(" ");
        int position = 0;

        for (int i = 0; i < substrings.length; i++) {
            if (!substrings[i].equals((templateSubstrings[i]))) {
                attributes[position].setValue(substrings[i]);
                position++;
            }
        }

        messageRepository.save(message);

        for (Attribute attribute : attributes) {
            attributeRepository.save(attribute);
        }
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

    public void deleteTemplate(Template template) {
        if(template.getId() == null) throw new TemplateWithThisIdDoesNotExistException();
        Template deletingTemplate = templateRepository.findById(template.getId())
                .orElseThrow(TemplateWithThisIdDoesNotExistException::new);

        templateRepository.delete(deletingTemplate);
    }

}
