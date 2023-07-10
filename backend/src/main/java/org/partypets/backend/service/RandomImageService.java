package org.partypets.backend.service;


import org.partypets.backend.model.RandomImage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Objects;


@Service
public class RandomImageService {

    private final WebClient webClient;

    private @Value("${api.unsplash.access.key}") String accessKey;

    public RandomImageService(
            @Value("${randomCatImage-api.url}") String url
    ) {
        this.webClient = WebClient.create(url);
    }

    public RandomImage getRandomCatImage() {
        ResponseEntity<RandomImage> responseEntity = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/photos/random")
                        .queryParam("query", "cat")
                        .queryParam("client_id", accessKey)
                        .build())
                .retrieve()
                .toEntity(RandomImage.class)
                .block();

        return Objects.requireNonNull(responseEntity).getBody();
    }
}

