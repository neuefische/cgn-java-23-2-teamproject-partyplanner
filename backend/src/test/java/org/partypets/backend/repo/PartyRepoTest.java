package org.partypets.backend.repo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.partypets.backend.model.Party;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class PartyRepoTest {
    PartyRepo partyRepo;

    @BeforeEach
    void setup() {
        this.partyRepo = new PartyRepo();
    }

    @Test
    void expectAddedPartyInList_whenPartyIsAdded() {
        //GIVEN
        Party newParty = new Party(null, new Date(), "Home", "Dog-Bday");
        List<Party> expected = new ArrayList<>(List.of(newParty));
        //WHEN
        this.partyRepo.add(newParty);
        List<Party> actual = this.partyRepo.getParties();
        //THEN
        assertEquals(expected, actual);
    }

    @Test
    void expectParty_whenGetByIdAbc() {
        //GIVEN
        Party expected = new Party("abc", new Date(), "Home", "Dog-Bday");
        this.partyRepo.setParties(List.of(expected));
        //WHEN
        Party actual = this.partyRepo.getById("abc");
        //THEN
        assertEquals(expected, actual);
    }

    @Test
    void expectNull_whenIdIsNotFound() {
        //GIVEN
        this.partyRepo.setParties(new ArrayList<>());
        //WHEN
        Party actual = this.partyRepo.getById("abc");
        //THEN
        assertNull(actual);
    }
}