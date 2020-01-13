package com.netcracker.parser.controllers;

import com.netcracker.parser.services.implementations.ParserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping("/parser")
public class ParserController {
    private final ParserServiceImpl parserService;
    private static final Logger logger = LoggerFactory.getLogger(ParserController.class);

    public ParserController(ParserServiceImpl parserService) {
        this.parserService = parserService;
    }


    @PostMapping
    public void parseString(@RequestBody String string) {
        logger.info("Parsing string: " + string);

        parserService.parseString(string.trim());
    }
}
