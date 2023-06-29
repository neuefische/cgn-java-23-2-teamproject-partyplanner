package org.partypets.backend.service;

import org.junit.jupiter.api.Test;
import org.partypets.backend.model.Diet;
import org.partypets.backend.model.Guest;
import org.partypets.backend.model.Party;
import org.partypets.backend.repo.PartyRepo;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;


class PartyServiceTest {

    PartyRepo partyRepo = mock(PartyRepo.class);
    PartyService partyService =new PartyService(partyRepo);

    @Test
    void expectListofAllParties_whenGettingTheList() {

        //given
        Party newParty = new Party("FakeDate", "Home", "Dog-Bday", List.of(new Guest("Gökhan", true, Diet.VEGETARIAN)));
        List<Party> expected = new ArrayList<>(List.of(newParty));

        //when
        when(partyRepo.getParties()) .thenReturn(expected);
        List<Party> actual = partyService.list();


        //then
        assertEquals(expected, actual);
        verify(partyRepo) .getParties(); //Test
    }

    @Test
    void addParty() {
    }
}