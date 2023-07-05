package org.partypets.backend.controller;


import lombok.RequiredArgsConstructor;
import org.partypets.backend.model.Party;
import org.partypets.backend.service.PartyService;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{id}")
    public Party getDetails(@PathVariable String id) {
        return this.partyService.getDetails(id);
    }


    @PostMapping
    public List<Party> addParty(@RequestBody Party party) {
        this.partyService.add(party);
        return this.partyService.list();
    }






}
