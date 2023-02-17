package org.example.controller;

import org.example.model.Player;
import org.example.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
public class PlayerPageController {
    @Autowired
    PlayerService playerService;

    @GetMapping("/players")
    public ModelAndView showPlayerListPage(Model model) {
        ModelAndView modelAndView = new ModelAndView();
        List<Player> playerList = playerService.fetchPlayerList();
        //model.addAttribute("players", playerList);
        modelAndView.setViewName("playerList");
        modelAndView.addObject("players", playerList);
        return modelAndView;
        //return "playerList";
    }

    @GetMapping("/player/add")
    public String addNewPlayer(Model model) {
        Player player = new Player();
        model.addAttribute("player", player);
        return "addPlayer";
    }

    @PostMapping("/save")
    public String savePlayer(@ModelAttribute("player") Player player) {
        playerService.savePlayer(player);
        return "redirect:/players";
    }

    @GetMapping("/player/edit/{id}")
    public String updatePlayerInfo(@PathVariable(value = "id") long id, Model model) {
        Player player = playerService.getById(id);
        model.addAttribute("player", player);
        return "editPlayer";
    }

    @GetMapping("/player/cancel/{id}")
    public String cancelPlayer(@PathVariable(value = "id") long id) {
        playerService.setPassivePlayer(id);
        return "redirect:/players";

    }

    @GetMapping("/player/activate/{id}")
    public String activate(@PathVariable(value = "id") long id) {
        playerService.setActivatePlayer(id);
        return "redirect:/players";

    }

}
