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

    public ResponseEntity<Map<String, String>> newParking(ParkingRequestModel parkingRequestModel) {
        Map<String, String> message = new HashMap<>();

        boolean userAbleToNewParkingRequest = userAbleToNewParkingRequest();
        if (!userAbleToNewParkingRequest) {
            message.put("message", "user already has an opened parking request!");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }

        var spot = spotsRepository.findSpotById(parkingRequestModel.getSpotId());

        if (spot.isPresent() && spot.get().isAvailable()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime departureDate = LocalDateTime.parse(parkingRequestModel.getDepartureDate(), formatter);

            if (departureDate.isBefore(LocalDateTime.now())) {
                message.put("message", "departureDate must be after than entryDate");
                return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
            }

            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            Long userId = userRepository.findUserByUsername(username).get().getId();

            Map<Object, Object> parkingMap = new HashMap<>();
            parkingMap.put("entryDate", LocalDateTime.now());
            parkingMap.put("departureDate", departureDate);
            parkingMap.put("userId", userId);
            parkingMap.put("spotId", parkingRequestModel.getSpotId());


            defaultRepository.newParking(parkingMap);
            defaultRepository.spotsAvailabilityShifter(spot.get().getId(), false);

            message.put("message", "new parking created");
            return ResponseEntity.status(HttpStatus.CREATED).body(message);
        }

        message.put("message", "spot is already in use");
        return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
    }

    public boolean userAbleToNewParkingRequest() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();


        var currentUser = userRepository.findUserByUsername(authentication.getName()).get();
        var historyCurrentUser = historyRepository.findByUserIdAndIsFinished(currentUser.getId(), false);


        return historyCurrentUser.isEmpty();
    }

    public ResponseEntity<Map<String, String>> completeParking() {
        Map<String, String> responseMessage = new HashMap<>();

        var authentication = SecurityContextHolder.getContext().getAuthentication();

        var userHistory = findHistory(authentication.getName());

        if (userHistory.size() == 0) {
            System.out.println("here");
            responseMessage.put("message", "user doesn't have any parking request created!");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseMessage);
        }

        var currentParking = userHistory.stream().filter(historyModel -> !historyModel.isFinished()).findFirst();


        if (currentParking.isEmpty()) {
            responseMessage.put("message", "there's not any opened parking request!");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseMessage);
        }

        var entryDate= currentParking.get().getEntryDate();
        var departureDate = currentParking.get().getDepartureDate();

        var timeSpent = (float) ChronoUnit.HOURS.between(entryDate, departureDate);

        BigDecimal valueToPay = new BigDecimal(timeSpent + 1).multiply(currentParking.get().getSpot().getSpotType().getValue());


        responseMessage.put("valueToPay", valueToPay.toEngineeringString());

        return ResponseEntity.ok().body(responseMessage);
    }
}
