package com.parkingSpot.app.controllers.users;

import com.parkingSpot.app.models.HistoryModel;
import com.parkingSpot.app.models.SpotsModel;
import com.parkingSpot.app.models.UsernamePasswordAuthenticationRequest;
import com.parkingSpot.app.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @GetMapping("/history")
    public List<HistoryModel> getUserHistory(Authentication authentication) {
        return userService.findHistory(authentication.getName());
    }

    @PostMapping("/signing")
    public ResponseEntity<Map<String, String>> signingUser(@RequestBody UsernamePasswordAuthenticationRequest user) {
        var userResponse = new HashMap<String, String>();
        userResponse.put("message", "user created");
        userService.createNewUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
    }

}
