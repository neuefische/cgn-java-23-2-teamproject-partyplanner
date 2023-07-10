package org.partypets.backend.model;
public record RandomImage(
        String id,
        String alt_description,
        RandomImageUrl urls
) {}
