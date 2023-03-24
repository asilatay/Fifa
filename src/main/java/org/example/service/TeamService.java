package org.example.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.model.Team;
import org.example.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;

    Logger logger = LogManager.getLogger(TeamService.class);

    @Transactional(readOnly = false)
    public Team saveTeam(Team team) throws Exception {
        if (team == null) {
            throw new Exception("Entity not found");
        }
        if (team.getId() <= 0) {
            team.setIsActive(true);

            team.setCreated(LocalDateTime.now());

        }
        team.setUpdated(LocalDateTime.now());
        return teamRepository.save(team);
    }

    public List<Team> fetchTeamList()
    {
        logger.info("INFO Getting teams from database");

        List<Team> teams =
                teamRepository.findAll();
        logger.info(teams);
        return teams;
    }

    public Team getById(long teamId) {
        if (teamId <= 0L) {
            logger.info("teamId cannot be less or equal than zero. teamId: " + teamId);
            return null;
        }
        return teamRepository.getReferenceById(teamId);
    }

    @Transactional(readOnly = false)
    public void setPassiveTeam(long teamId) {
        if (teamId <= 0L) {
            logger.info("teamId cannot be less or equal than zero. Team: " + teamId);
            return;
        }

        Team team = getById(teamId);
        if (team == null) {
            logger.info("Team not found for that teamId: " + teamId);
            return;
        }

        team.setIsActive(false);
        try {
            saveTeam(team);
        } catch (Exception e) {
            logger.error(e);
        }
    }

    @Transactional(readOnly = false)
    public void setActivateTeam(long teamId) {
        if (teamId <= 0L) {
            logger.info("teamId cannot be less or equal than zero. TeamId: " + teamId);
            return;
        }

        Team team = getById(teamId);
        if (team == null) {
            logger.info("Team not found for that TeamId: " + teamId);
            return;
        }

        team.setIsActive(true);
        try {
            saveTeam(team);
        } catch (Exception e) {
            logger.error(e);
        }
    }

    public List<Team> getAvailableTeams() {
        List<Team> availableTeams = teamRepository.getAvailableTeams();

        return availableTeams;
    }
}
