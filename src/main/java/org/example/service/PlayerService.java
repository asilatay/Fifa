package org.example.service;

import org.example.model.Player;
import org.example.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class PlayerService {
    @Autowired
    private PlayerRepository playerRepository;
    public Player savePlayer(Player player)
    {
       return playerRepository.save(player);
    }
    public List<Player> fetchPlayerList()
    {
       return (List<Player>)
       playerRepository.findAll();
    }
}
