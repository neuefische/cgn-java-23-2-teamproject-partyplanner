package org.partypets.backend.service;

import lombok.RequiredArgsConstructor;
import org.partypets.backend.model.Party;
import org.partypets.backend.model.UuIdService;
import org.partypets.backend.repo.PartyRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor

public class PartyService {

    private final PartyRepo partyRepo;

    private final UuIdService uuIdService;


    public List<Party> list() {
        return this.partyRepo.getParties();
    }

    public void add(Party party) {
        String id = uuIdService.getRandomId();
        party.setId(id);
        this.partyRepo.add(party);
    }
}
