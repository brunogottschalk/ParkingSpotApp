package com.parkingSpot.app.controllers.users;

import com.parkingSpot.app.models.UsernamePasswordAuthenticationRequest;
import com.parkingSpot.app.services.SigningService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/signup")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class SignUpController {
    private final SigningService signingService;

    public SignUpController(SigningService signingService) {
        this.signingService = signingService;
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> signingUser(@RequestBody UsernamePasswordAuthenticationRequest user) {
        return signingService.createNewUser(user);
    }
}
