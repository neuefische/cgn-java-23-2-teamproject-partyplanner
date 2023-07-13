package org.partypets.backend.model;

import java.util.List;

public record Quiz(String id, String question, List<QuizAnswer> answers) {
    public Quiz unsolved() {
        return new Quiz(this.id, this.question,
                this.answers.stream()
                        .map(answer -> new QuizAnswer(answer.answerText(), false))
                        .toList());
    }
}
