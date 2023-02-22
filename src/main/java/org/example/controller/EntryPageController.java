package org.example.controller;

import org.example.model.Player;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
public class EntryPageController {
    @RequestMapping("/")
    public ModelAndView getEntryPage()
    {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("mainPage");
        return modelAndView;
    }
}
