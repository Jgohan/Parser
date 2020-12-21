package com.netcracker.parser.controllers;

import com.netcracker.parser.entities.Message;
import com.netcracker.parser.entities.User;
import com.netcracker.parser.services.ParserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping("/parser")
public class ParserController {
    private final ParserService parserService;
    private static final Logger logger = LoggerFactory.getLogger(ParserController.class);

    public ParserController(ParserService parserService) {
        this.parserService = parserService;
    }


    @PostMapping
    public ResponseEntity<?> parseString(
            @RequestBody String string,
            @AuthenticationPrincipal User author
    ) {
        string = string.replaceAll("[\r\n]","");
        logger.info("Parsing string: {}", string.replaceAll("[\r\n]",""));

        return parserService.parseString(string.trim(), author);
    }

    @GetMapping
    public List<Message> getStrings(
            @RequestParam(name = "templateId") String templateId
    ) {
        logger.info(
                "Get strings for template with id {}",
                templateId.replaceAll("[\r\n]","")
        );

        return parserService.getMessages(Long.parseLong(templateId));
    }
}
