package org.partypets.backend.controller;

import org.partypets.backend.model.Quiz;
import org.partypets.backend.service.QuizService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/quiz")
public class QuizController {

    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @GetMapping
    public Quiz getRandomQuiz() {
        return this.quizService.getRandomUnsolved();
    }

    @GetMapping("{id}")
    public Quiz checkAnswer(@PathVariable String id) {
        return this.quizService.getByIdSolved(id);
    }
}
