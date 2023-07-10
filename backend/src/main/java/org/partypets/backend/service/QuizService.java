package org.partypets.backend.service;

import org.partypets.backend.model.Quiz;
import org.partypets.backend.model.QuizAnswer;
import org.partypets.backend.model.QuizWithoutSolution;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.util.Optionals;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.security.SecureRandom;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class QuizService {

    private final SecureRandom random = new SecureRandom();
    private final WebClient webClient;

    public QuizService(
            @Value("${quiz-api.url}")
            String webclientUrl) {
        this.webClient = WebClient.create(webclientUrl);
    }

    public QuizWithoutSolution getRandom() {
        List<QuizWithoutSolution> response = Objects.requireNonNull(webClient.get()
                .uri("/quiz")
                .retrieve()
                .toEntityList(QuizWithoutSolution.class)
                .block()).getBody();
        assert response != null;
        int randint = random.nextInt(response.size());
        return response.get(randint);
    }

    public boolean checkAnswer(String id, String answer) {
        List<Quiz> response = Objects.requireNonNull(webClient.get()
                .uri("/quiz")
                .retrieve()
                .toEntityList(Quiz.class)
                .block()).getBody();
        assert response != null;
        for (Quiz quiz : response) {
            if (quiz.id().equals(id)) {
                quiz.answers().stream().findAny()
            }
        }
    }
}
