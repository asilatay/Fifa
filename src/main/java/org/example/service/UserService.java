package org.example.service;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.model.Position;
import org.example.model.User;
import org.example.repository.PositionRepository;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class UserService {

    @Autowired
    private UserRepository userRepository;

    Logger logger = LogManager.getLogger(UserService.class);

    @Transactional(readOnly = false)
    public User saveUser(User user) throws Exception {
        if (user == null) {
            throw new Exception("Entity not found");
        }
        if (user.getId() <= 0) {
            user.setIsActive(true);
            user.setCreated(LocalDateTime.now());
        }

        user.setUpdated(LocalDateTime.now());
        return userRepository.save(user);
    }

    public List<User> fetchUserList()
    {
        logger.info("INFO Getting users from database");
        List<User> users =
                userRepository.findAll();

        return users;
    }

    public List<User> getAvailableUserList() {
        List<User> userList = userRepository.getAvailableUserList();
        return userList;
    }

    public User getById(long userId) {
        if (userId <= 0L) {
            logger.info("userId cannot be less or equal than zero. userId: " + userId);
            return null;
        }
        return userRepository.getReferenceById(userId);
    }

    @Transactional(readOnly = false)
    public void setPassiveUser(long userId) {
        if (userId <= 0L) {
            logger.info("userId cannot be less or equal than zero. UserId: " + userId);
            return;
        }

        User user = getById(userId);
        if (user == null) {
            logger.info("User not found for that UserId: " + userId);
            return;
        }

        user.setIsActive(false);
        try {
            saveUser(user);
        } catch (Exception e) {
            logger.error(e);
        }
    }

    @Transactional(readOnly = false)
    public void activateUser(long userId) {
        if (userId <= 0L) {
            logger.info("userId cannot be less or equal than zero. userId: " + userId);
            return;
        }

        User user = getById(userId);
        if (user == null) {
            logger.info("userId not found for that userId: " + userId);
            return;
        }

        user.setIsActive(true);
        try {
            saveUser(user);
        } catch (Exception e) {
            logger.error(e);
        }
    }
}
