package com.example.server.controller;

import com.example.server.dto.WishDto;
import com.example.server.model.CreateWishRequest;
import com.example.server.model.UpdateWishRequest;
import com.example.server.model.User;
import com.example.server.model.Wish;
import com.example.server.repository.WishRepository;
import com.example.server.service.AuthService;
import com.example.server.service.WishService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wish")
public class WishController {

    private final WishService service;
    private final WishRepository wishRepository;
    private final AuthService authService;

    public WishController(
            WishService service,
            WishRepository wishRepository,
            AuthService authService
    ) {
        this.service = service;
        this.wishRepository = wishRepository;
        this.authService = authService;
    }

    // 🎁 Создать желание
    @PostMapping
    public WishDto create(
            @RequestHeader("Authorization") String auth,
            @RequestBody CreateWishRequest req
    ) {
        User user = authService.requireUser(auth);
        return service.createWish(user.getId(), req);
    }

    // ✏️ Обновить желание
    @PutMapping("/{id}")
    public WishDto update(
            @RequestHeader("Authorization") String auth,
            @PathVariable Long id,
            @RequestBody UpdateWishRequest req
    ) {
        User user = authService.requireUser(auth);
        return service.updateWish(user.getId(), id, req);
    }

    // 🗑 Удалить желание
    @DeleteMapping("/{id}")
    public void delete(
            @RequestHeader("Authorization") String auth,
            @PathVariable Long id
    ) {
        User user = authService.requireUser(auth);

        // защита от удаления чужого
        Wish wish = wishRepository.findById(id).orElseThrow();
        if (!wish.getUserId().equals(user.getId())) {
            throw new RuntimeException("Access denied");
        }

        wishRepository.deleteById(id);
    }

    // ⭐ Избранное
    @PostMapping("/{id}/favorite")
    public WishDto toggleFavorite(
            @RequestHeader("Authorization") String auth,
            @PathVariable Long id
    ) {
        User user = authService.requireUser(auth);
        return service.toggleFavorite(user.getId(), id);
    }

    // ✅ Выполнено
    @PostMapping("/{id}/done")
    public WishDto toggleDone(
            @RequestHeader("Authorization") String auth,
            @PathVariable Long id
    ) {
        User user = authService.requireUser(auth);
        return service.toggleDone(user.getId(), id);
    }

    // 📋 Мои желания
    @GetMapping("/my")
    public List<WishDto> myWishes(
            @RequestHeader("Authorization") String auth
    ) {
        User user = authService.requireUser(auth);
        return service.getMyWishes(user.getId());
    }

    // 💞 Желания партнёра
    @GetMapping("/partner")
    public List<WishDto> partnerWishes(
            @RequestHeader("Authorization") String auth
    ) {
        User user = authService.requireUser(auth);
        return service.getPartnerWishes(user.getPartnerId());
    }
}
