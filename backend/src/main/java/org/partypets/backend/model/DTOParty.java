package org.partypets.backend.model;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DTOParty {
    @NotNull
    @FutureOrPresent
    private Date date;
    @NotBlank
    @Size(min = 3, max = 25, message = "Must be between 3 and 25 characters long")
    private String location;
    @NotBlank
    @Size(min = 3, max = 25, message = "Must be between 3 and 25 characters long")
    private String theme;
}
