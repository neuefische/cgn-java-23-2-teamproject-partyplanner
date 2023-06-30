package org.partypets.backend.service;

import org.junit.jupiter.api.Test;
import org.partypets.backend.model.Party;
import org.partypets.backend.repo.PartyRepo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


class PartyServiceTest {

    PartyRepo partyRepo = mock(PartyRepo.class);
    PartyService partyService = new PartyService(partyRepo);

    @Test
    void expectListOfAllParties_whenGettingTheList() {

        //given
        Party newParty = new Party(null, new Date(), "Home", "Dog-Bday");
        List<Party> expected = new ArrayList<>(List.of(newParty));

        //when
        when(partyRepo.getParties()).thenReturn(expected);
        List<Party> actual = partyService.list();

        //then
        assertEquals(expected, actual);
        verify(partyRepo).getParties();
    }
}
