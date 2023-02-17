package org.example.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.model.Player;
import org.example.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

@Service
@Transactional(readOnly = true)
public class PlayerService {
    @Autowired
    private PlayerRepository playerRepository;

    Logger logger = LogManager.getLogger(PlayerService.class);
    public Player savePlayer(Player player)
    {
       return playerRepository.save(player);
    }
    public List<Player> fetchPlayerList()
    {
        logger.info("INFO Getting players from database");
        logger.debug("DEBUG Getting players from database");
        logger.error("ERROR - NO ERROR FROM DATABASE");


        //

        List<Player> players =
                playerRepository.findAll();
        logger.info(players);
       return players;
    }
}
