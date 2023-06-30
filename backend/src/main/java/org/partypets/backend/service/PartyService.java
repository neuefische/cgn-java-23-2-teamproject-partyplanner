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
        return this.partyRepo.getParties();
    }

    public Party add(Party party) {
        String id = uuIdService.getRandomId();
        party.setId(id);
        return this.partyRepo.add(party);
    }
}
