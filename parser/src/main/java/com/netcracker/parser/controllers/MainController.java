package com.netcracker.parser.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping
    public String loadPage(){
        System.out.println("LOAD");
        return "main";
    }
}
