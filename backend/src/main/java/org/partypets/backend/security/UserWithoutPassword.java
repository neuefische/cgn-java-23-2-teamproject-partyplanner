package org.partypets.backend.security;

public record UserWithoutPassword(
        String id,
        String username
) {
}
