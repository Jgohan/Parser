package com.netcracker.parser.controllers;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/parser")
public class ParserController {
    private List<String> strings = new ArrayList<>();

    @PostMapping
    public void parse(@RequestBody String string){
        System.out.println(string);
        strings.add(string);
    }

    @GetMapping
    public List<String> getStrings() {
        System.out.println("GET");
        return strings;
    }
}
