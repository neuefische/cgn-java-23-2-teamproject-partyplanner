package org.partypets.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Party {

    private String id;
    private Date date;
    private String location;
    private String theme;

}
