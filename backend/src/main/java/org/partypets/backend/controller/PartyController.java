package org.partypets.backend.controller;
import jakarta.validation.Valid;
import org.partypets.backend.model.PartyWithoutId;
import org.partypets.backend.model.Party;
import org.partypets.backend.service.PartyService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/parties")
public class PartyController {

    private final PartyService partyService;

    public PartyController(PartyService partyService) {
        this.partyService = partyService;
    }

    @GetMapping
    public List<Party> listParties() {
        return this.partyService.list();
    }

    @GetMapping("/{id}")
    public Party getDetails(@PathVariable String id) {
        return this.partyService.getDetails(id);
    }


    @PostMapping
    public List<Party> addParty(@Valid @RequestBody PartyWithoutId newParty) {
        this.partyService.add(newParty);
        return this.partyService.list();
    }

    @PutMapping("/{id}")
    public Party update(@PathVariable String id, @Valid @RequestBody PartyWithoutId newParty) {
        return partyService.edit(id, newParty);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        partyService.delete(id);
    }

}
