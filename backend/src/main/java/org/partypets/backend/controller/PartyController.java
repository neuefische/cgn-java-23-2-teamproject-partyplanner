package org.partypets.backend.controller;


import lombok.RequiredArgsConstructor;
import org.partypets.backend.model.DTOParty;
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
    public List<Party> addParty(@RequestBody DTOParty dtoParty) {
        this.partyService.add(dtoParty);
        return this.partyService.list();
    }

    @PutMapping("/{id}")
    public Party update(@PathVariable String id, @RequestBody DTOParty dtoParty) {
        return partyService.edit(id, dtoParty);
    }


}
