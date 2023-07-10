package org.partypets.backend.model;

import java.util.List;

public record QuizWithoutSolution(String id, String question, List<String> answers) {
}
