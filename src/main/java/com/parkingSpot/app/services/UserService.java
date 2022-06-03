package com.parkingSpot.app.services;

import com.parkingSpot.app.models.*;
import com.parkingSpot.app.repositories.HistoryRepository;
import com.parkingSpot.app.repositories.SpotsRepository;
import com.parkingSpot.app.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {
    private final HistoryRepository historyRepository;
    private final SpotsRepository spotsRepository;
    private final UserRepository userRepository;

    @Autowired
    public UserService(HistoryRepository historyRepository, SpotsRepository spotsRepository,
            UserRepository userRepository) {
        this.historyRepository = historyRepository;
        this.spotsRepository = spotsRepository;
        this.userRepository = userRepository;
    }

    public List<SpotsModel> findSpots() {
        return spotsRepository.findAll();
    }

    public List<HistoryModel> findHistory(String username) {
        Optional<UserModel> user = userRepository.findUserByUsername(username);
        return historyRepository.findHistoryByUserId(user.get().getId());
    }
}
