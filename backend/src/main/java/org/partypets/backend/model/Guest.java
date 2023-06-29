package org.partypets.backend.model;

import lombok.Data;

import java.util.UUID;

@Data
public class Guest {

    private final String id;
    private String name;
    private boolean rsvp;
    private Diet diet;


    public Guest(String name, boolean rsvp, Diet diet) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.rsvp = rsvp;
        this.diet = diet;
    }
}
