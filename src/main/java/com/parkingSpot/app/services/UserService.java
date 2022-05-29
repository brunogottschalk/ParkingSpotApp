package com.parkingSpot.app.services;

import com.parkingSpot.app.models.HistoryModel;
import com.parkingSpot.app.models.SpotsModel;
import com.parkingSpot.app.models.UserModel;
import com.parkingSpot.app.models.UsernamePasswordAuthenticationRequest;
import com.parkingSpot.app.repositories.HistoryRepository;
import com.parkingSpot.app.repositories.SpotsRepository;
import com.parkingSpot.app.repositories.UserInsertRepository;
import com.parkingSpot.app.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final HistoryRepository historyRepository;
    private final SpotsRepository spotsRepository;
    private final UserRepository userRepository;

    private final UserInsertRepository userInsertRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(HistoryRepository historyRepository, SpotsRepository spotsRepository,
                       UserRepository userRepository, UserInsertRepository userInsertRepository,
                       PasswordEncoder passwordEncoder) {
        this.historyRepository = historyRepository;
        this.spotsRepository = spotsRepository;
        this.userRepository = userRepository;
        this.userInsertRepository = userInsertRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<HistoryModel> findHistory(String username) {
        Optional<UserModel> user = userRepository.findUserByUsername(username);
        return historyRepository.findHistoryByUserId(user.get().getId()).orElseThrow(() -> new NullPointerException());
    }

    public List<SpotsModel> findSpots() {
        return spotsRepository.findAll();
    }

    public void createNewUser(UsernamePasswordAuthenticationRequest user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userInsertRepository.insertNewUser(user);
    }

}
