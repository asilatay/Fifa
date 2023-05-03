package org.example.service;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.model.*;
import org.example.repository.MixOperationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class MixOperationService {

    @Autowired
    private MixOperationRepository mixOperationRepository;

    @Autowired
    private TeamService teamService;

    @Autowired
    private PlayerService playerService;

    Logger logger = LogManager.getLogger(MixOperationService.class);

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


        mixOperation.setCreated(LocalDateTime.now());
        mixOperation.setUpdated(LocalDateTime.now());
        mixOperation.setIsActive(true);

        //group players by role
        Map<Position, List<Player>> positionPlayerListMap = groupPlayersByPosition(availablePlayers);

        // choose a team
        Team selectedTeam = chooseRandomTeam(availableTeams);
        mixOperation.setTeam(selectedTeam);
        List<Player> playerList= chooseRandomPlayers(positionPlayerListMap);
        List<MixOperationDetail> mixOperationDetailList=new ArrayList<MixOperationDetail>();
        for(Player p : playerList){
            MixOperationDetail mixOperationDetail=new MixOperationDetail();
            mixOperationDetail.setPlayer(p);
            mixOperationDetail.setMixOperation(mixOperation);
            mixOperationDetailList.add(mixOperationDetail);
        }

        mixOperation.getDetails().addAll(mixOperationDetailList);

        return mixOperationRepository.save(mixOperation);
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

    private List<Player> chooseRandomPlayers(Map<Position, List<Player>> positionListMap){
        List<Player> playerList=new ArrayList<Player>();
        playerList.addAll(getRandomPlayers(positionListMap,ServiceParameterConstants.GK_COUNT,"GK"));
        playerList.addAll(getRandomPlayers(positionListMap,ServiceParameterConstants.RB_COUNT,"RB"));
        playerList.addAll(getRandomPlayers(positionListMap,ServiceParameterConstants.LB_COUNT,"LB"));
        playerList.addAll(getRandomPlayers(positionListMap,ServiceParameterConstants.CB_COUNT,"CB"));
        playerList.addAll(getRandomPlayers(positionListMap,ServiceParameterConstants.MDO_COUNT,"MDO"));
        playerList.addAll(getRandomPlayers(positionListMap,ServiceParameterConstants.MO_COUNT,"MO"));
        playerList.addAll(getRandomPlayers(positionListMap,ServiceParameterConstants.AMC_COUNT,"AMC"));
        playerList.addAll(getRandomPlayers(positionListMap,ServiceParameterConstants.MR_COUNT,"MR"));
        playerList.addAll(getRandomPlayers(positionListMap,ServiceParameterConstants.ML_COUNT,"ML"));
        playerList.addAll(getRandomPlayers(positionListMap,ServiceParameterConstants.ST_COUNT,"ST"));
        return playerList;
    }

    private List<Player> getRandomPlayers(Map<Position, List<Player>> positionListMap,int positionCount,String positionCode){
        List<Player> playerList=new ArrayList<Player>();
        Random randomizer = new Random();
        List<Player> availablePlayerList=new ArrayList<Player>();
        for (Map.Entry<Position, List<Player>> entry : positionListMap.entrySet()) {
           if(entry.getKey().getCode().equals(positionCode)){
               availablePlayerList = entry.getValue();
               break;
           }
        }

        for(int i=0;i<positionCount;i++){
            Player chosenPlayer = availablePlayerList.get(randomizer.nextInt(availablePlayerList.size()));
            playerList.add(chosenPlayer);
            availablePlayerList.remove(chosenPlayer);
        }
        return playerList;
    }
    @Transactional(readOnly = false)
    public void setActivateOperation(long operationId) {
        if (operationId <= 0L) {
            logger.info("OperationId cannot be less or equal than zero. OperationId: " + operationId);
            return;
        }

        MixOperation mixoperation = mixOperationRepository.getReferenceById(operationId);
        if (mixoperation == null) {
            logger.info("Operation not found for that OperationId: " + operationId);
            return;
        }

        mixoperation.setIsActive(true);
        try {
            mixOperationRepository.save(mixoperation);
        } catch (Exception e) {
            logger.error(e);
        }
    }

    @Transactional(readOnly = false)
    public void setPassiveOperation(long operationId) {
        if (operationId <= 0L) {
            logger.info("OperationId cannot be less or equal than zero. OperationId: " + operationId);
            return;
        }

        MixOperation mixoperation = mixOperationRepository.getReferenceById(operationId);
        if (mixoperation == null) {
            logger.info("Operation not found for that OperationId: " + operationId);
            return;
        }

        mixoperation.setIsActive(false);
        try {
            mixOperationRepository.save(mixoperation);
        } catch (Exception e) {
            logger.error(e);
        }
    }


}
