package org.partypets.backend.service;

import org.junit.jupiter.api.Test;
import org.partypets.backend.model.Party;
import org.partypets.backend.model.UuIdService;
import org.partypets.backend.repo.PartyRepo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


class PartyServiceTest {

    PartyRepo partyRepo = mock(PartyRepo.class);
    UuIdService uuIdService = mock(UuIdService.class);
    PartyService partyService = new PartyService(partyRepo, uuIdService);

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

    @Test
    void expectId_whenAddedParty() {
        //given
        Party newParty = new Party(null, new Date(), "Home", "Dog-Bday");
        Party expected = new Party("abc", new Date(), "Home", "Dog-Bday");

        //when
        when(uuIdService.getRandomId()).thenReturn("abc");
        when(partyRepo.add(newParty)).thenReturn(expected);
        Party actual = partyService.add(newParty);

        //then
        assertEquals(expected, actual);
        verify(uuIdService).getRandomId();
        verify(partyRepo).add(newParty);
    }


    @Test
    void expectParty_whenGettingPartyDetails() {
        //given
        Party expected = new Party("abc", new Date(), "Home", "Dog-Bday");

        //when
        when(partyRepo.getById("abc")).thenReturn(expected);
        Party actual = partyService.getDetails("abc");

        //then
        assertEquals(expected, actual);
        verify(partyRepo).getById("abc");
    }
}
