package org.partypets.backend.controller;

import org.partypets.backend.model.RandomImage;
import org.partypets.backend.service.RandomImageService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")

public class RandomImageController {

    private final RandomImageService randomImageService;

    public RandomImageController(RandomImageService randomImageService) {
        this.randomImageService = randomImageService;
    }

    @GetMapping("/randomCatImage")
    RandomImage getRandomCatImage() {
        return randomImageService.getRandomCatImage();
    }
}
