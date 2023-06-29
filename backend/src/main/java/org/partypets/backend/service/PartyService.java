package org.partypets.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor

public class PartyService {

    private final PartyRepo partyRepo;

    public List<Party> list() {
        return this.partyRepo.getParties();
    }

}
