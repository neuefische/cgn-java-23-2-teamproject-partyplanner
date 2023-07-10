package org.partypets.backend.model;

import java.util.List;

public record Quiz(String id, String question, List<QuizAnswer> answers) {
}
