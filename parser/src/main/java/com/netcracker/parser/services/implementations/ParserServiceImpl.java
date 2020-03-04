package com.netcracker.parser.services.implementations;

import com.netcracker.parser.entities.*;
import com.netcracker.parser.exceptions.TemplateStringWasNotIdentifiedException;
import com.netcracker.parser.exceptions.TemplateWithThisIdDoesNotExistException;
import com.netcracker.parser.repositories.AttributeRepository;
import com.netcracker.parser.repositories.MessageRepository;
import com.netcracker.parser.repositories.TemplateRepository;
import com.netcracker.parser.services.ParserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import static com.netcracker.parser.services.Constants.ATTRIBUTE;

@Service
public class ParserServiceImpl implements ParserService {
    private final TemplateRepository templateRepository;
    private final MessageRepository messageRepository;
    private final AttributeRepository attributeRepository;

    public ParserServiceImpl(TemplateRepository templateRepository,
                             MessageRepository messageRepository,
                             AttributeRepository attributeRepository) {
        this.templateRepository = templateRepository;
        this.messageRepository = messageRepository;
        this.attributeRepository = attributeRepository;
    }


    private Template identifyTemplate(String string) {
        Iterable<Template> templates = templateRepository.findAll();
        int containedSubstringsNumber, attributesNumber;

        for (Template template : templates) {
            String[] templateSubstrings = template.getTemplateString().split(ATTRIBUTE);

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

            if (attributesNumber == template.countAttributes()
                    && containedSubstringsNumber == templateSubstrings.length
                    && string.split(" ").length == template.getTemplateString().split(" ").length)
                return template;
        }

        throw new TemplateStringWasNotIdentifiedException();
    }

    @Override
    public ResponseEntity<?> parseString(String string, User author) {
        try {
            Template template = identifyTemplate(string);
            Message message = new Message(
                    template,
                    author,
                    ZonedDateTime.now(ZoneId.of("UTC")).withNano(0)
            );

            List<AttributeName> attributeNames = template.getAttributesNames();
            Attribute[] attributes = new Attribute[template.countAttributes()];
            for (int i = 0; i < attributes.length; i++) {
                attributes[i] = new Attribute(
                        attributeNames.get(i),
                        message
                );
            }

            String[] substrings = string.split(" ");
            String[] templateSubstrings = template.getTemplateString().split(" ");
            int position = 0;

            for (int i = 0; i < substrings.length; i++) {
                if (templateSubstrings[i].equals(ATTRIBUTE)) {
                    attributes[position].setValue(substrings[i]);
                    position++;
                }
            }

            messageRepository.save(message);

            for (Attribute attribute : attributes) {
                attributeRepository.save(attribute);
            }

            return ResponseEntity.ok("String has been parsed");
        } catch (TemplateStringWasNotIdentifiedException e) {
            return new ResponseEntity<>(
                    e.getMessage(),
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    @Override
    public List<Message> getMessages(Long templateId) {
        Template template = templateRepository.findById(templateId)
                .orElseThrow(TemplateWithThisIdDoesNotExistException::new);
        return template.getMessages();
    }
}
