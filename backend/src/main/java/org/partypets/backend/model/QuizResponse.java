package org.partypets.backend.model;

import lombok.Data;

import java.util.List;

@Data
public class QuizResponse {
    private List<Quiz> quizzes;
}
