package com.parkingSpot.app.controllers.users;

import com.parkingSpot.app.models.HistoryModel;
import com.parkingSpot.app.models.ParkingRequestModel;
import com.parkingSpot.app.models.SpotsModel;
import com.parkingSpot.app.models.UsernamePasswordAuthenticationRequest;
import com.parkingSpot.app.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/spots")
    public List<SpotsModel> getAvailableSpots() {
        return userService.findSpots();
    }

    @PostMapping("/parking")
    public ResponseEntity<Map<String, String>> newParking(@RequestBody ParkingRequestModel parkingRequestModel) throws ParseException {
        return userService.newParking(parkingRequestModel);
    }

    @GetMapping("/history")
    public List<HistoryModel> getUserHistory(Authentication authentication) {
        return userService.findHistory(authentication.getName());
    }

    @GetMapping("/parking/complete")
    public ResponseEntity<Map<String, String>> completeParking() {
        return userService.completeParking();
    }
}
