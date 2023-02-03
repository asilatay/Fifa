package org.example.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PlayerPageController {
    @RequestMapping("/players")
    public String getEntryPage()
    {
        return "Hello Players";
    }

}
