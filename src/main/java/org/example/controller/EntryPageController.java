package org.example.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class EntryPageController {
    @RequestMapping("/")
    public String getEntryPage()
    {
        return "Hello User";
    }
}
