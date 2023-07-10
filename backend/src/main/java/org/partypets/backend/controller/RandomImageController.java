package org.partypets.backend.controller;

import org.partypets.backend.model.DTOParty;
import org.partypets.backend.model.Party;
import org.partypets.backend.model.RandomImage;
import org.partypets.backend.service.PartyService;
import org.partypets.backend.service.RandomImageService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
