package org.example.controller;

import org.example.model.MixOperation;
import org.example.service.MixOperationService;
import org.example.service.PlayerService;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
public class MixOperationController {
    @Autowired
    MixOperationService mixOperationService;

    @Autowired
    UserService userService;

    @Autowired
    PlayerService playerService;

    @GetMapping("/mixSquads")
    public ModelAndView showMixxedSquads() {
        ModelAndView modelAndView = new ModelAndView();
        List<MixOperation> operations = mixOperationService.fetchOperations();
        modelAndView.setViewName("mixOperationList");
        modelAndView.addObject("operations", operations);
        return modelAndView;
    }

    @GetMapping("/mixSquad/setParameters")
    public ModelAndView setParametersBeforeMix() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("mixOperationSetParameter");
        modelAndView.addObject("mixOperation", new MixOperation());
        modelAndView.addObject("manipulatedUserList", userService.getAvailableUserList());

        return modelAndView;
    }

    @PostMapping("/mixSquad/start")
    public ModelAndView startMixSquad(@ModelAttribute("mixOperation") MixOperation mixOperation) throws Exception{
        mixOperationService.startMixingSquads(mixOperation);
        return showMixxedSquads();
    }

    @GetMapping("/mixOperation/detail/{id}")
    public ModelAndView showMixDetail(@PathVariable(value = "id") long id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("mixedListDetail");
        modelAndView.addObject("playerList", playerService.getMixedPlayers(id));
        return  modelAndView;
    }

    @GetMapping("/mixOperation/cancel/{id}")
    public ModelAndView cancelOperation(@PathVariable(value = "id") long id) {
        mixOperationService.setPassiveOperation(id);
        return showMixxedSquads();

    }

    @GetMapping("/mixOperation/activate/{id}")
    public ModelAndView activateOperation(@PathVariable(value = "id") long id) {
        mixOperationService.setActivateOperation(id);
        return showMixxedSquads();

    }
}
