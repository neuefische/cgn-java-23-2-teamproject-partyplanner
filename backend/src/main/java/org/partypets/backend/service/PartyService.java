package org.partypets.backend.service;

import lombok.RequiredArgsConstructor;
import org.partypets.backend.model.DTOParty;
import org.partypets.backend.model.Party;
import org.partypets.backend.model.UuIdService;
import org.partypets.backend.repo.PartyRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PartyService {

    private final PartyRepo partyRepo;

    private final UuIdService uuIdService;

    public PartyService(PartyRepo partyRepo, UuIdService uuIdService) {
        this.partyRepo = partyRepo;
        this.uuIdService = uuIdService;
    }

    public List<Party> list() {
        return this.partyRepo.findAll();
    }

    public Party add(DTOParty newParty) {
        String id = uuIdService.getRandomId();
        Party party = new Party(id, newParty.getDate(), newParty.getLocation(), newParty.getTheme());
        return this.partyRepo.insert(party);
    }

    public Party getDetails(String id) {
        return this.partyRepo.findById(id).orElseThrow();
    }


    public Party edit(String id, DTOParty newParty) {
        Party editedParty = new Party(id, newParty.getDate(), newParty.getLocation(), newParty.getTheme());
        return this.partyRepo.save(editedParty);
    }

    public void delete(String id) {
        this.partyRepo.deleteById(id);
    }
}
