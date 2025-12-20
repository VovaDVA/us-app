package com.example.server.controller;

import com.example.server.model.CreateWishRequest;
import com.example.server.model.UpdateWishRequest;
import com.example.server.model.WishDto;
import com.example.server.repository.WishRepository;
import com.example.server.service.WishService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wish")
public class WishController {

    private final WishService service;
    private final WishRepository wishRepository;

    public WishController(WishService service, WishRepository wishRepository) {
        this.service = service;
        this.wishRepository = wishRepository;
    }

    @PostMapping
    public WishDto create(
            @RequestParam("userId") Long userId,
            @RequestBody CreateWishRequest req
    ) {
        return service.createWish(userId, req);
    }

    @PutMapping("/{id}")
    public WishDto update(
            @PathVariable Long id,
            @RequestParam("userId") Long userId,
            @RequestBody UpdateWishRequest req
    ) {
        return service.updateWish(userId, id, req);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        wishRepository.deleteById(id);
    }


    @PostMapping("/{id}/favorite")
    public WishDto toggleFavorite(
            @RequestParam("userId") Long userId,
            @PathVariable Long id
    ) {
        return service.toggleFavorite(userId, id);
    }

    @PostMapping("/{id}/done")
    public WishDto toggleDone(
            @RequestParam("userId") Long userId,
            @PathVariable Long id
    ) {
        return service.toggleDone(userId, id);
    }

    @GetMapping("/my")
    public List<WishDto> myWishes(@RequestParam("userId") Long userId) {
        return service.getMyWishes(userId);
    }

    @GetMapping("/partner")
    public List<WishDto> partnerWishes(@RequestParam("partnerId") Long partnerId) {
        return service.getPartnerWishes(partnerId);
    }
}
