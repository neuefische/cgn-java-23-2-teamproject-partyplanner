package org.partypets.backend.model;

import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
public class Party {

    private final String id;
    private String date;
    private String location;
    private String theme;
    private List<Guest> guests;

    public Party(String date, String location, String theme) {
        this.id = UUID.randomUUID().toString();
        this.date = date;
        this.location = location;
        this.theme = theme;
    }

    public Party(String date, String location, String theme, List<Guest> guests) {
        this(date, location, theme);
        this.guests = guests;
    }
}
