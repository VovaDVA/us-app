package com.example.server.controller;

import com.example.server.model.MoodType;
import com.example.server.model.User;
import com.example.server.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/{id}/mood")
    public MoodType getMood(@PathVariable Long id) {
        return userRepository.findById(id)
                .map(User::getMood)
                .orElse(MoodType.CALM);
    }

    @PutMapping("/{id}/mood")
    public void updateMood(@PathVariable Long id, @RequestBody MoodType mood) {
        userRepository.findById(id).ifPresent(user -> {
            user.setMood(mood);
            userRepository.save(user);
        });
    }

}
