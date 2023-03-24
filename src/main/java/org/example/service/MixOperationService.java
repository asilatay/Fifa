package org.example.service;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.model.*;
import org.example.repository.MixOperationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional(readOnly = true)
public class MixOperationService {

    @Autowired
    private MixOperationRepository mixOperationRepository;

    @Autowired
    private TeamService teamService;

    @Autowired
    private PlayerService playerService;


    Logger logger = LogManager.getLogger(MixOperationService.class);

    @Transactional
    public MixOperation startMixingSquads(MixOperation mixOperation) throws Exception {
       //validation
        if (mixOperation.getUser() == null) {
            logger.error("Uygun kullanıcı olmadığı için mix işlemi yapılamaz. Lütfen eski operasyonları iptal edin.");
            return null;
        }

        List<Team> availableTeams = teamService.getAvailableTeams();
        if (CollectionUtils.isEmpty(availableTeams)) {
            logger.error("Uygun takım bulunamadığı için mix işlemi yapılamaz. Lütfen eski operasyonları iptal edin.");
            return null;
        }

        List<Player> availablePlayers = playerService
                .getAvailablePlayers(mixOperation.getMinPlayerRating(), mixOperation.getMaxPlayerRating());
        if (CollectionUtils.isEmpty(availablePlayers)) {
            logger.error("Uygun oyuncu bulunamadığı için mix işlemi yapılamaz. Lütfen eski operasyonları iptal edin.");
            return null;
        }

        //group players by role
        Map<Position, List<Player>> positionPlayerListMap = groupPlayersByPosition(availablePlayers);

        // choose a team
        Team selectedTeam = chooseRandomTeam(availableTeams);
        mixOperation.setTeam(selectedTeam);

        return null;
    }

    public List<MixOperation> fetchOperations() {
        List<MixOperation> operations =
                mixOperationRepository.findAll();

        return operations;
    }

    private Map<Position, List<Player>> groupPlayersByPosition(List<Player> availablePlayers) {
        Map<Position, List<Player>> positionPlayerListMap = new HashMap<>();

        for (Player p : availablePlayers) {
            List<Position> playerPositionList = p.getPositions();
            for (Position pos : playerPositionList) {
                List<Player> playerList = new ArrayList<>();
                if (positionPlayerListMap.containsKey(pos)) {
                    playerList = positionPlayerListMap.get(pos);

                }
                playerList.add(p);
                positionPlayerListMap.put(pos, playerList);
            }
        }
        return positionPlayerListMap;
    }

    private Team chooseRandomTeam(List<Team> availableTeams) {
        Random randomizer = new Random();
        Team chosenTeam = availableTeams.get(randomizer.nextInt(availableTeams.size()));

        return chosenTeam;
    }
}
