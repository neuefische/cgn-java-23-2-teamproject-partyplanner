package org.partypets.backend.service;

import lombok.RequiredArgsConstructor;
import org.partypets.backend.model.DTOParty;
import org.partypets.backend.model.Party;
import org.partypets.backend.model.UuIdService;
import org.partypets.backend.repo.PartyRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class PartyService {

    private final PartyRepo partyRepo;

    private final UuIdService uuIdService;


    public List<Party> list() {
        return this.partyRepo.findAll();
    }

    public Party add(DTOParty dtoParty) {
        String id = uuIdService.getRandomId();
        Party newParty = new Party(id, dtoParty.getDate(), dtoParty.getLocation(), dtoParty.getTheme());
        return this.partyRepo.insert(newParty);
    }

    public Party getDetails(String id) {
        return this.partyRepo.findById(id).orElseThrow();
    }


    public Party edit(String id, DTOParty dtoParty) {
        Party editedParty = new Party(id, dtoParty.getDate(), dtoParty.getLocation(), dtoParty.getTheme());
        return this.partyRepo.save(editedParty);
    }

    public void delete(String id) {
        this.partyRepo.deleteById(id);
    }
}
