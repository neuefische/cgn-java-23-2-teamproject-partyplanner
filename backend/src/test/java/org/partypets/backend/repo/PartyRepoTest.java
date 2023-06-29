package org.partypets.backend.repo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.partypets.backend.model.Diet;
import org.partypets.backend.model.Guest;
import org.partypets.backend.model.Party;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PartyRepoTest {
    private PartyRepo partyRepo;

    @BeforeEach
    void setUp() {
        this.partyRepo = new PartyRepo();
    }

    @Test
    void expectAddedPartyInList_WhenAddedParty() {

        //GIVEN
        Party newParty = new Party("FakeDate", "Home", "Dog-Bday", List.of(new Guest("Gökhan", true, Diet.VEGETARIAN)));
        List<Party> expected = new ArrayList<>(List.of(newParty));

        //WHEN
        this.partyRepo.add(newParty);

        //THEN
        assertEquals(expected, this.partyRepo.getParties());

    }
}