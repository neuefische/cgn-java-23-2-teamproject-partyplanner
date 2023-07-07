package org.partypets.backend.service;

import lombok.RequiredArgsConstructor;
import org.partypets.backend.model.Quiz;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Objects;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class QuizService {

    private final WebClient webClient = WebClient.create("https://quiz.capstone-project.de/api");

    public Quiz getRandom() {
        List<Quiz> response = Objects.requireNonNull(webClient.get()
                .uri("/quiz")
                .retrieve()
                .toEntityList(Quiz.class)
                .block()).getBody();
        assert response != null;
        int randint = new Random().nextInt(response.size());
        return response.get(randint);
    }
}
