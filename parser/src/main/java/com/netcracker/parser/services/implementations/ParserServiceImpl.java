package com.netcracker.parser.services.implementations;

import com.netcracker.parser.entities.Attribute;
import com.netcracker.parser.entities.Message;
import com.netcracker.parser.entities.Template;
import com.netcracker.parser.entities.User;
import com.netcracker.parser.exceptions.TemplateStringWasNotIdentifiedException;
import com.netcracker.parser.repositories.AttributeRepository;
import com.netcracker.parser.repositories.MessageRepository;
import com.netcracker.parser.repositories.TemplateRepository;
import com.netcracker.parser.security.Response;
import com.netcracker.parser.services.ParserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import static com.netcracker.parser.services.Constants.att;

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


    @Override
    public Template identifyTemplate(String string) {
        Iterable<Template> templates = templateRepository.findAll();
        int containedSubstringsNumber, attributesNumber;

        for (Template template : templates) {
            String[] templateSubstrings = template.getTemplateString().split(att);

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
            Message message = new Message(template, author);

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

            return ResponseEntity.ok(
                    new Response("String has been parsed")
            );
        } catch (TemplateStringWasNotIdentifiedException e) {
            return new ResponseEntity<>(
                    new Response(e.getMessage()),
                    HttpStatus.BAD_REQUEST
            );
        }
    }
}
