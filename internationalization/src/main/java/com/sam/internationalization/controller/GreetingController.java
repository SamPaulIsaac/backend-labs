package com.sam.internationalization.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
@RequestMapping("/greeting")
public class GreetingController {

    @Autowired
    private MessageSource messageSource;

    @GetMapping
    public String greet(Locale locale) {
        String message1 = messageSource.getMessage("greeting", null, locale);
        String message2 = messageSource.getMessage("farewell", null, locale);
        return message1.concat(" ").concat(message2);
    }
}

