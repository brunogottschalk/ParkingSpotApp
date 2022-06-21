package com.parkingSpot.app.controllers.users;

import com.parkingSpot.app.models.*;
import com.parkingSpot.app.services.ParkingService;
import com.parkingSpot.app.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {

    private final UserService userService;
    private final ParkingService parkingService;

    @Autowired
    public UserController(UserService userService, ParkingService parkingService) {
        this.userService = userService;
        this.parkingService = parkingService;
    }

    @GetMapping("/spots")
    public List<SpotsModel> getAvailableSpots() {
        return userService.findSpots();
    }

    @PostMapping("/parking")
    public ResponseEntity<Map<String, String>> newParking(@RequestBody ParkingRequestModel parkingRequestModel)
            throws ParseException {
        return parkingService.newParking(parkingRequestModel);
    }

    @GetMapping("/history")
    public ResponseEntity<Object> getUserHistory(Authentication authentication) {
        return userService.findHistory(authentication.getName());
    }

    @GetMapping("/parking/complete")
    public ResponseEntity<Map<String, String>> parkingDetails() {
        return parkingService.parkingDetails();
    }

    @PostMapping("/parking/complete")
    public ResponseEntity<Map<String, String>> completeParking(
            @RequestBody CompleteParkingRequest receivedParkingValue) {
        return parkingService.completeParking(receivedParkingValue);
    }
}
