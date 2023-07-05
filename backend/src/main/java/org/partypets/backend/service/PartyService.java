package org.partypets.backend.service;

import lombok.RequiredArgsConstructor;
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

    public Party add(Party party) {
        String id = uuIdService.getRandomId();
        party.setId(id);
        return this.partyRepo.insert(party);
    }

    public Party getDetails(String id) {
        return this.partyRepo.findById(id).orElseThrow();
    }


    public Party edit(Party party) {
        return this.partyRepo.save(party);
    }
}
