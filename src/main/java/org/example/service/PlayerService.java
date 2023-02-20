package org.example.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.model.Player;
import org.example.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class PlayerService {
    @Autowired
    private PlayerRepository playerRepository;

    Logger logger = LogManager.getLogger(PlayerService.class);

    @Transactional(readOnly = false)
    public Player savePlayer(Player player) throws Exception {
        if (player == null) {
            throw new Exception("Entity not found");
        }
        if (player.getId() <= 0) {
            player.setIsActive(true);

            player.setCreated(LocalDateTime.now());

        }
        player.setUpdated(LocalDateTime.now());
        return playerRepository.save(player);
    }
    public List<Player> fetchPlayerList()
    {
        logger.info("INFO Getting players from database");

        List<Player> players =
                playerRepository.findAll();
        logger.info(players);
       return players;
    }

    public Player getById(long playerId) {
        if (playerId <= 0L) {
            logger.info("PlayerId cannot be less or equal than zero. PlayerId: " + playerId);
            return null;
        }
        return playerRepository.getReferenceById(playerId);
    }

    @Transactional(readOnly = false)
    public void setPassivePlayer(long playerId) {
        if (playerId <= 0L) {
            logger.info("PlayerId cannot be less or equal than zero. PlayerId: " + playerId);
            return;
        }

        Player player = getById(playerId);
        if (player == null) {
            logger.info("Player not found for that PlayerId: " + playerId);
            return;
        }

        player.setIsActive(false);
        try {
            savePlayer(player);
        } catch (Exception e) {
            logger.error(e);
        }
    }

    @Transactional(readOnly = false)
    public void setActivatePlayer(long playerId) {
        if (playerId <= 0L) {
            logger.info("PlayerId cannot be less or equal than zero. PlayerId: " + playerId);
            return;
        }

        Player player = getById(playerId);
        if (player == null) {
            logger.info("Player not found for that PlayerId: " + playerId);
            return;
        }

        player.setIsActive(true);
        try {
            savePlayer(player);
        } catch (Exception e) {
            logger.error(e);
        }
    }
}
