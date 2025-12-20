package com.example.server.model;

public class WishDto {

    public Long id;
    public String title;
    public String description;
    public String link;
    public String categoryIcon;
    public boolean isDone;
    public boolean isFavorite;

    public WishDto(Long id, String title, String description, String link,
                   String categoryIcon,
                   boolean isDone, boolean isFavorite) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.link = link;
        this.categoryIcon = categoryIcon;
        this.isDone = isDone;
        this.isFavorite = isFavorite;
    }

    public static WishDto from(Wish e) {
        return new WishDto(
                e.getId(),
                e.getTitle(),
                e.getDescription(),
                e.getLink(),
                e.getCategoryIcon(),
                e.isDone(),
                e.isFavorite()
        );
    }
}
