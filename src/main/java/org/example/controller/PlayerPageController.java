package org.example.controller;

import org.example.model.Player;
import org.example.service.PlayerService;
import org.example.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
public class PlayerPageController {
    @Autowired
    PlayerService playerService;

    @Autowired
    PositionService positionService;

    @GetMapping("/players")
    public ModelAndView showPlayerListPage() {
        ModelAndView modelAndView = new ModelAndView();
        List<Player> playerList = playerService.fetchPlayerList();
        modelAndView.setViewName("playerList");
        modelAndView.addObject("players", playerList);
        return modelAndView;
    }

    @GetMapping("/player/add")
    public ModelAndView addNewPlayer() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("playerAdd");
        modelAndView.addObject("player", new Player());
        modelAndView.addObject("positions", positionService.fetchPositionList());
        return modelAndView;
    }

    @PostMapping("/player/save")
    public ModelAndView savePlayer(@ModelAttribute("player") Player player) throws Exception {
        playerService.savePlayer(player);
        return showPlayerListPage();
    }

    @GetMapping("/player/edit/{id}")
    public ModelAndView updatePlayerInfo(@PathVariable(value = "id") long id, Model model) {
        ModelAndView modelAndView = new ModelAndView();
        Player player = playerService.getById(id);
        modelAndView.setViewName("playerEdit");
        modelAndView.addObject("player", player);
        return modelAndView;
    }

    @GetMapping("/player/cancel/{id}")
    public ModelAndView cancelPlayer(@PathVariable(value = "id") long id) {
        playerService.setPassivePlayer(id);
        return showPlayerListPage();

    }

    @GetMapping("/player/activate/{id}")
    public ModelAndView activate(@PathVariable(value = "id") long id) {
        playerService.setActivatePlayer(id);
        return showPlayerListPage();

    }

}
