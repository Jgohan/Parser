package com.netcracker.parser.controllers;

import com.netcracker.parser.services.implementations.ParserServiceImpl;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping("/parser")
public class ParserController {
    private final ParserServiceImpl parserService;

    public ParserController(ParserServiceImpl parserService) {
        this.parserService = parserService;
    }


    @PostMapping
    public void parseString(@RequestBody String string) {
        System.out.println(string);

        parserService.parseString(string.trim());
    }
}
