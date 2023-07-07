package org.partypets.backend.controller;

import lombok.RequiredArgsConstructor;
import org.partypets.backend.model.Quiz;
import org.partypets.backend.service.QuizService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/quiz")
@RequiredArgsConstructor
public class QuizController {

    private final QuizService quizService;

    @GetMapping
    public Quiz getRandomQuiz() {
        return this.quizService.getRandom();
    }
}
