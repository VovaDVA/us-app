package com.example.server.controller;

import com.example.server.dto.AuthResponse;
import com.example.server.dto.RegisterRequest;
import com.example.server.model.AuthToken;
import com.example.server.model.User;
import com.example.server.repository.AuthTokenRepository;
import com.example.server.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final AuthTokenRepository tokenRepository;

    @PostMapping("/register")
    public AuthResponse register(@RequestBody RegisterRequest req) {

        // 1️⃣ Загружаем всех пользователей
        List<User> users = userRepository.findAll();

        if (users.size() >= 2) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "All users already registered"
            );
        }

        // 2️⃣ Создаём пользователя
        User user = new User();
        user.setName(req.name());

        userRepository.save(user);

        // 3️⃣ Проставляем partnerId
        if (users.size() == 1) {
            User partner = users.get(0);
            user.setPartnerId(partner.getId());
            partner.setPartnerId(user.getId());

            userRepository.save(partner);
            userRepository.save(user);
        }

        // 4️⃣ Генерируем токен
        String tokenValue = UUID.randomUUID().toString();

        AuthToken token = new AuthToken();
        token.setToken(tokenValue);
        token.setUser(user);

        tokenRepository.save(token);

        return new AuthResponse(
                user.getId(),
                user.getPartnerId(),
                tokenValue,
                user.getName()
        );
    }
}
