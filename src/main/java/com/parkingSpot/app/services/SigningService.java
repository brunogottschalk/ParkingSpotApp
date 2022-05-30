package com.parkingSpot.app.services;

import com.parkingSpot.app.models.UsernamePasswordAuthenticationRequest;
import com.parkingSpot.app.repositories.DefaultRepository;
import com.parkingSpot.app.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class SigningService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final DefaultRepository defaultRepository;

    public SigningService(UserRepository userRepository, PasswordEncoder passwordEncoder, DefaultRepository defaultRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.defaultRepository = defaultRepository;
    }

    public ResponseEntity<Map<String, String>> createNewUser(UsernamePasswordAuthenticationRequest user) {
        Map<String, String> message = new HashMap<>();

        var userChecker = userRepository.findUserByUsername(user.getUsername());

        if (userChecker.isPresent()) {
            message.put("message", "user already exists");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        defaultRepository.insertNewUser(user);

        message.put("message", "user created");
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }

}
