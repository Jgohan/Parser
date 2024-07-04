package com.netcracker.parser.services.implementations;

import com.netcracker.parser.entities.Attribute;
import com.netcracker.parser.entities.Message;
import com.netcracker.parser.entities.Template;
import com.netcracker.parser.entities.User;
import com.netcracker.parser.exceptions.StringContainsCodeCharSequenceException;
import com.netcracker.parser.exceptions.TemplateStringWasNotIdentifiedException;
import com.netcracker.parser.exceptions.TemplateWithThisIdDoesNotExistException;
import com.netcracker.parser.repositories.AttributeRepository;
import com.netcracker.parser.repositories.MessageRepository;
import com.netcracker.parser.repositories.TemplateRepository;
import com.netcracker.parser.security.Response;
import com.netcracker.parser.services.ParserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.List;

import static com.netcracker.parser.services.Constants.ATTRIBUTE;

@Service
@RequiredArgsConstructor
public class ParserServiceImpl implements ParserService {

    private final TemplateRepository templateRepository;
    private final MessageRepository messageRepository;
    private final AttributeRepository attributeRepository;


    @Override
    public ResponseEntity<?> parseString(String string, User author) {
        try {
            if (string.contains(ATTRIBUTE)) {
                throw new StringContainsCodeCharSequenceException();
            }

            var template = identifyTemplate(string);
            var message = new Message(
                    template,
                    author,
                    ZonedDateTime.now(ZoneId.of("UTC")).withNano(0)
            );

            var attributes = new Attribute[template.countAttributes()];
            for (int i = 0; i < attributes.length; i++) {
                attributes[i] = new Attribute(i + 1, message);
            }

            var substrings = string.split(" ");
            var templateSubstrings = template.getTemplateString().split(" ");
            int position = 0;

            for (int i = 0; i < substrings.length; i++) {
                if (!substrings[i].equals((templateSubstrings[i]))) {
                    attributes[position].setValue(substrings[i]);
                    position++;
                }
            }

            messageRepository.save(message);

            for (var attribute : attributes) {
                attributeRepository.save(attribute);
            }

            return ResponseEntity.ok(
                    new Response("String has been parsed")
            );
        } catch (TemplateStringWasNotIdentifiedException
                | StringContainsCodeCharSequenceException e) {
            return new ResponseEntity<>(
                    new Response(e.getMessage()),
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    @Override
    public List<Message> getMessages(Long templateId) {
        var template = templateRepository
                .findById(templateId)
                .orElseThrow(TemplateWithThisIdDoesNotExistException::new);

        return template.getMessages();
    }

    private Template identifyTemplate(String string) {
        var templates = templateRepository.findAll();
        int containedSubstringsNumber, attributesNumber;

        for (var template : templates) {
            var templateSubstrings = template.getTemplateString().split(ATTRIBUTE);
            containedSubstringsNumber = 0;
            var stringBuffer = string;

            for (var substring : templateSubstrings) {
                if (stringBuffer.contains(substring)) {
                    containedSubstringsNumber++;

                    if (!substring.isEmpty()) {
                        stringBuffer = stringBuffer.replaceFirst(substring, " ");
                    }
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
}
