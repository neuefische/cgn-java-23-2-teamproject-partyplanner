package org.partypets.backend.service;

import org.partypets.backend.model.Quiz;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.security.SecureRandom;
import java.util.List;
import java.util.Objects;

@Service
public class QuizService {

    private SecureRandom random = new SecureRandom();
    private final WebClient webClient;

    public QuizService(
            @Value("${quiz-api.url}")
            String webclientUrl) {
        this.webClient = WebClient.create(webclientUrl);
    }

    public Quiz getRandom() {
        List<Quiz> response = Objects.requireNonNull(webClient.get()
                .uri("/quiz")
                .retrieve()
                .toEntityList(Quiz.class)
                .block()).getBody();
        assert response != null;
        int randint = random.nextInt(response.size());
        return response.get(randint);
    }
}
