package com.parkingSpot.app.services;

import com.parkingSpot.app.models.*;
import com.parkingSpot.app.repositories.HistoryRepository;
import com.parkingSpot.app.repositories.SpotsRepository;
import com.parkingSpot.app.repositories.DefaultRepository;
import com.parkingSpot.app.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class UserService {
    private final HistoryRepository historyRepository;
    private final SpotsRepository spotsRepository;
    private final UserRepository userRepository;
    private final DefaultRepository defaultRepository;


    @Autowired
    public UserService(HistoryRepository historyRepository, SpotsRepository spotsRepository,
                       UserRepository userRepository, DefaultRepository defaultRepository) {
        this.historyRepository = historyRepository;
        this.spotsRepository = spotsRepository;
        this.userRepository = userRepository;
        this.defaultRepository = defaultRepository;
    }

    public List<SpotsModel> findSpots() {
        return spotsRepository.findAll();
    }

    public List<HistoryModel> findHistory(String username) {
        Optional<UserModel> user = userRepository.findUserByUsername(username);
        return historyRepository.findHistoryByUserId(user.get().getId());
    }
}
