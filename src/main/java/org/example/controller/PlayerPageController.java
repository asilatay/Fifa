package org.example.controller;

import org.example.model.Player;
import org.example.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PlayerPageController {
    @Autowired
    PlayerService playerService;
    @RequestMapping("/players")
    public List<Player> getEntryPage()
    {
        return playerService.fetchPlayerList();
    }

}
