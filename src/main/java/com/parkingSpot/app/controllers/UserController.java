package com.parkingSpot.app.controllers;

import com.parkingSpot.app.Repositories.UserRepository;
import com.parkingSpot.app.models.UserModel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/user")
    public Iterable<UserModel> getUsers() {
        return userRepository.findAll();
    }

}
