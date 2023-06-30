package org.partypets.backend.repo;

import org.junit.jupiter.api.Test;
import org.partypets.backend.model.Party;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PartyRepoTest {
    PartyRepo partyRepo;

    @Test
    void expectAddedPartyInList_whenPartyIsAdded() {
        this.partyRepo = new PartyRepo();
        //GIVEN
        Party newParty = new Party(null, new Date(), "Home", "Dog-Bday");
        List<Party> expected = new ArrayList<>(List.of(newParty));
        //WHEN
        this.partyRepo.add(newParty);
        List<Party> actual = this.partyRepo.getParties();

        //THEN
        assertEquals(expected, actual);
    }
}