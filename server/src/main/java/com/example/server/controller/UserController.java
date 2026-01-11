package com.example.server.controller;

import com.example.server.model.MoodType;
import com.example.server.model.User;
import com.example.server.repository.UserRepository;
import com.example.server.service.AuthService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepository;
    private final AuthService authService;

    public UserController(UserRepository userRepository, AuthService authService) {
        this.userRepository = userRepository;
        this.authService = authService;
    }

    @GetMapping("/mood")
    public MoodType getMood(
            @RequestHeader("Authorization") String auth
    ) {
        User user = authService.requireUser(auth);
        return user.getMood();
    }

    @PutMapping("/mood")
    public void updateMood(
            @RequestHeader("Authorization") String auth,
            @RequestBody MoodType mood
    ) {
        User user = authService.requireUser(auth);

        user.setMood(mood);
        userRepository.save(user);
    }

}
