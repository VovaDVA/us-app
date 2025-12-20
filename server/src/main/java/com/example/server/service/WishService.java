package com.example.server.service;

import com.example.server.model.CreateWishRequest;
import com.example.server.model.UpdateWishRequest;
import com.example.server.model.Wish;
import com.example.server.model.WishDto;
import com.example.server.repository.WishRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class WishService {

    private final WishRepository wishRepository;

    public WishService(WishRepository wishRepository) {
        this.wishRepository = wishRepository;
    }

    public List<WishDto> getMyWishes(Long userId) {
        return wishRepository.findAllByUserId(userId)
                .stream()
                .map(WishDto::from)
                .toList();
    }

    public List<WishDto> getPartnerWishes(Long partnerId) {
        return wishRepository.findAllByUserId(partnerId)
                .stream()
                .map(WishDto::from)
                .toList();
    }

    public WishDto createWish(Long userId, CreateWishRequest req) {
        Wish wish = new Wish(
                userId,
                req.title,
                req.description,
                req.link,
                req.isFavorite,
                req.categoryIcon
        );
        wishRepository.save(wish);
        return WishDto.from(wish);
    }

    public WishDto updateWish(Long userId, Long id, UpdateWishRequest req) {
        Wish wish = wishRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Wish not found"));

        if (!wish.getUserId().equals(userId))
            throw new RuntimeException("Access denied");

        if (req.title != null) wish.setTitle(req.title);
        if (req.description != null) wish.setDescription(req.description);
        if (req.link != null) wish.setLink(req.link);
        if (req.categoryIcon != null) wish.setCategoryIcon(req.categoryIcon);
        if (req.isDone != null) wish.setDone(req.isDone);
        if (req.isFavorite != null) wish.setFavorite(req.isFavorite);

        wishRepository.save(wish);
        return WishDto.from(wish);
    }

    public WishDto toggleFavorite(Long userId, Long id) {
        Wish wish = wishRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Wish not found"));

        if (!wish.getUserId().equals(userId))
            throw new RuntimeException("Access denied");

        wish.setFavorite(!wish.isFavorite());
        wishRepository.save(wish);
        return WishDto.from(wish);
    }

    public WishDto toggleDone(Long userId, Long id) {
        Wish wish = wishRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Wish not found"));

        if (!wish.getUserId().equals(userId))
            throw new RuntimeException("Access denied");

        wish.setDone(!wish.isDone());
        wishRepository.save(wish);
        return WishDto.from(wish);
    }
}
