package org.example.service;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.model.Player;
import org.example.model.Position;
import org.example.repository.PlayerRepository;
import org.example.repository.PositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class PositionService {

    @Autowired
    private PositionRepository positionRepository;

    Logger logger = LogManager.getLogger(PositionService.class);

    @Transactional(readOnly = false)
    public Position savePosition(Position position) throws Exception {
        if (position == null) {
            throw new Exception("Entity not found");
        }
        if (position.getId() <= 0) {
            position.setIsActive(true);

            position.setCreated(LocalDateTime.now());

        }
        position.setUpdated(LocalDateTime.now());
        return positionRepository.save(position);
    }

    public List<Position> fetchPositionList()
    {
        logger.info("INFO Getting positions from database");

        List<Position> positions =
                positionRepository.findAll();
        logger.info(positions);
        return positions;
    }

    public Position getById(long positionId) {
        if (positionId <= 0L) {
            logger.info("PositionId cannot be less or equal than zero. PositionId: " + positionId);
            return null;
        }
        return positionRepository.getReferenceById(positionId);
    }

    @Transactional(readOnly = false)
    public void setPassivePosition(long positionId) {
        if (positionId <= 0L) {
            logger.info("PositionId cannot be less or equal than zero. Position: " + positionId);
            return;
        }

        Position position = getById(positionId);
        if (position == null) {
            logger.info("Position not found for that PositionId: " + positionId);
            return;
        }

        position.setIsActive(false);
        try {
            savePosition(position);
        } catch (Exception e) {
            logger.error(e);
        }
    }

    @Transactional(readOnly = false)
    public void setActivatePosition(long positionId) {
        if (positionId <= 0L) {
            logger.info("PositionId cannot be less or equal than zero. PositionId: " + positionId);
            return;
        }

        Position position = getById(positionId);
        if (position == null) {
            logger.info("Position not found for that PositionId: " + positionId);
            return;
        }

        position.setIsActive(true);
        try {
            savePosition(position);
        } catch (Exception e) {
            logger.error(e);
        }
    }
}
