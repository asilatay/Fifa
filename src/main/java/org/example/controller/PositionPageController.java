package org.example.controller;

import org.example.model.Player;
import org.example.model.Position;
import org.example.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
public class PositionPageController {

    @Autowired
    PositionService positionService;

    @GetMapping("/positions")
    public ModelAndView showPositionListPage() {
        ModelAndView modelAndView = new ModelAndView();
        List<Position> positionList = positionService.fetchPositionList();
        modelAndView.setViewName("positionList");
        modelAndView.addObject("positions", positionList);
        return modelAndView;
    }

    @GetMapping("/position/add")
    public ModelAndView addNewPosition() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("positionAdd");
        modelAndView.addObject("position", new Position());
        return modelAndView;
    }

    @PostMapping("/position/save")
    public ModelAndView savePosition(@ModelAttribute("position") Position position) throws Exception {
        positionService.savePosition(position);
        return showPositionListPage();
    }

    @GetMapping("/position/edit/{id}")
    public ModelAndView updatePositionInfo(@PathVariable(value = "id") long id, Model model) {
        ModelAndView modelAndView = new ModelAndView();
        Position position = positionService.getById(id);
        modelAndView.setViewName("positionEdit");
        modelAndView.addObject("position", position);
        return modelAndView;
    }

    @GetMapping("/position/cancel/{id}")
    public ModelAndView cancelPosition(@PathVariable(value = "id") long id) {
        positionService.setPassivePosition(id);
        return showPositionListPage();

    }

    @GetMapping("/position/activate/{id}")
    public ModelAndView activate(@PathVariable(value = "id") long id) {
        positionService.setActivatePosition(id);
        return showPositionListPage();

    }
}
