package org.partypets.backend.controller;

import org.partypets.backend.model.Quiz;
import org.partypets.backend.model.QuizWithoutSolution;
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
    public QuizWithoutSolution getRandomQuiz() {
        return this.quizService.getRandom();
    }

    @GetMapping("{id}")
    public Quiz checkAnswer(@PathVariable String id) {
        return this.quizService.getById(id);
    }
}
