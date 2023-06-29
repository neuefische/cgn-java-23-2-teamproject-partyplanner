package org.partypets.backend.controller;


import lombok.RequiredArgsConstructor;
import org.partypets.backend.model.Party;
import org.partypets.backend.service.PartyService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/parties")
@RequiredArgsConstructor
public class PartyController {


    private final PartyService partyService;

    @GetMapping
    public List<Party> listParties() {
        return this.partyService.list();
    }

}