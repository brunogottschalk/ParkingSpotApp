package com.parkingSpot.app.services;

import com.parkingSpot.app.models.CompleteParkingRequest;
import com.parkingSpot.app.models.HistoryModel;
import com.parkingSpot.app.models.ParkingRequestModel;
import com.parkingSpot.app.repositories.DefaultRepository;
import com.parkingSpot.app.repositories.HistoryRepository;
import com.parkingSpot.app.repositories.SpotsRepository;
import com.parkingSpot.app.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ParkingService {

    private SpotsRepository spotsRepository;
    private UserRepository userRepository;
    private DefaultRepository defaultRepository;
    private HistoryRepository historyRepository;

    public ParkingService(SpotsRepository spotsRepository, UserRepository userRepository, DefaultRepository defaultRepository, HistoryRepository historyRepository) {
        this.spotsRepository = spotsRepository;
        this.userRepository = userRepository;
        this.defaultRepository = defaultRepository;
        this.historyRepository = historyRepository;
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
            parkingMap.put("finished", false);


            defaultRepository.parkingShifter(parkingMap);
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

    public ResponseEntity<Map<String, String>> parkingDetails() {
        Map<String, String> responseMessage = new HashMap<>();

        var authentication = SecurityContextHolder.getContext().getAuthentication();

        var currentUser = userRepository.findUserByUsername(authentication.getName());
        var currentParking = historyRepository.findByUserIdAndIsFinished(currentUser.get().getId(), false);

        if (currentParking.isEmpty()) {
            responseMessage.put("message", "there's not any opened parking request!");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseMessage);
        }

        BigDecimal valueToPay = valueCalculator(currentParking.get());

        responseMessage.put("valueToPay", valueToPay.toPlainString());

        return ResponseEntity.ok().body(responseMessage);
    }

    public ResponseEntity<Map<String, String>> completeParking(CompleteParkingRequest receivedParkingValue) {
        Map<String, String> responseMessage = new HashMap<>();
        var authenticatedName = SecurityContextHolder.getContext().getAuthentication().getName();

        var user = userRepository.findUserByUsername(authenticatedName);
        var currentParking = historyRepository.findByUserIdAndIsFinished(user.get().getId(), false);

        if (currentParking.isEmpty()) {
            responseMessage.put("message", "there's not any opened parking request");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseMessage);
        }

        BigDecimal valueToPay = valueCalculator(currentParking.get());

        var receivedBigDecimalValue = new BigDecimal(receivedParkingValue.getValue());

        if (receivedBigDecimalValue.compareTo(valueToPay) < 0) {
            responseMessage.put("message", "insufficient money");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseMessage);
        }

        BigDecimal change = receivedBigDecimalValue.subtract(valueToPay);



        responseMessage.put("message", "successful payment");
        responseMessage.put("change", change.toPlainString());

        defaultRepository.finishPark(currentParking.get().getId());
        defaultRepository.spotsAvailabilityShifter(currentParking.get().getSpot().getId(), true);

        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }

    public BigDecimal valueCalculator(HistoryModel currentParking) {
        var entryDate= currentParking.getEntryDate();
        var departureDate = currentParking.getDepartureDate();

        var timeSpent = (float) ChronoUnit.HOURS.between(entryDate, departureDate);

        return new BigDecimal(timeSpent + 1).multiply(currentParking.getSpot().getSpotType().getValue());
    }
}
