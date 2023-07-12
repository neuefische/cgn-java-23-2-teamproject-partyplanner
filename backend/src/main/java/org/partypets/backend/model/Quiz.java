package org.partypets.backend.model;

import java.util.List;
import java.util.stream.Collectors;

public record Quiz(String id, String question, List<QuizAnswer> answers) {
    public Quiz unsolved() {
        return new Quiz(this.id, this.question,
                this.answers.stream()
                        .map(answer -> answer = new QuizAnswer(answer.answerText(), false))
                        .collect(Collectors.toList()));
    }
}
