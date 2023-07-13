package org.partypets.backend.security;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("users")
public record MongoUser(
        @Id
        String id,

        @NotBlank
        @Size(min = 3, max = 25, message = "A length between 3 and 25 characters is mandatory.")
        @Pattern(regexp = "\\S*", message = "Whitespace is not allowed")
        String username,

        @Pattern(regexp = "(?=^.{8,}$)(?=.*\\d)(?=.*[!@#$%^&*]+)(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).*$", message = "Must be at least 8 characters long, must include special character, must include digit, must include capital letter, must include non-capital letter")
        String password
) {


}
