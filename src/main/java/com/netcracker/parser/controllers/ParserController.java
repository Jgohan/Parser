package com.netcracker.parser.controllers;

import com.netcracker.parser.entities.Message;
import com.netcracker.parser.entities.User;
import com.netcracker.parser.services.ParserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping("/parser")
@RequiredArgsConstructor
@Log4j2
public class ParserController {

    private final ParserService parserService;


    @PostMapping
    public ResponseEntity<?> parseString(
            @RequestBody String string,
            @AuthenticationPrincipal User author
    ) {
        string = string.replaceAll("[\r\n]","");
        log.info("Parsing string: {}", string);

        return parserService.parseString(string.trim(), author);
    }

    @GetMapping
    public List<Message> getStrings(@RequestParam(name = "templateId") String templateId) {
        log.info(
                "Get strings for template with id {}",
                templateId.replaceAll("[\r\n]","")
        );

        return parserService.getMessages(Long.parseLong(templateId));
    }
}
