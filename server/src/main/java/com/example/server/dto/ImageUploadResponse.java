package com.example.server.dto;

public class ImageUploadResponse {
    public String smallUrl;
    public String largeUrl;

    public ImageUploadResponse(String smallUrl, String largeUrl) {
        this.smallUrl = smallUrl;
        this.largeUrl = largeUrl;
    }
}
