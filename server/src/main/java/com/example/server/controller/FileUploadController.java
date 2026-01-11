package com.example.server.controller;

import com.example.server.dto.ImageUploadResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.UUID;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class FileUploadController {

    @PostMapping("/diary-image")
    public ImageUploadResponse uploadDiaryImage(
            @RequestParam("image") MultipartFile image
    ) throws Exception {

        String uuid = UUID.randomUUID().toString();
        File dir = new File("uploads/diary");
        dir.mkdirs();

        BufferedImage original = ImageIO.read(image.getInputStream());

        // small
        BufferedImage small = resize(original, 400);
        String smallPath = "uploads/diary/" + uuid + "_small.jpg";
        ImageIO.write(small, "jpg", new File(smallPath));

        // large
        BufferedImage large = resize(original, 1200);
        String largePath = "uploads/diary/" + uuid + "_large.jpg";
        ImageIO.write(large, "jpg", new File(largePath));

        return new ImageUploadResponse(
                "/" + smallPath,
                "/" + largePath
        );
    }

    private BufferedImage resize(BufferedImage img, int targetWidth) {
        int width = img.getWidth();
        int height = img.getHeight();
        int targetHeight = (int) ((double) height / width * targetWidth);

        Image scaled = img.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);
        BufferedImage out = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);

        Graphics2D g = out.createGraphics();
        g.drawImage(scaled, 0, 0, null);
        g.dispose();

        return out;
    }
}
