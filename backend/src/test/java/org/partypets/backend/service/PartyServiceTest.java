package org.partypets.backend.service;

import org.junit.jupiter.api.Test;
import org.partypets.backend.model.DTOParty;
import org.partypets.backend.model.Party;
import org.partypets.backend.model.UuIdService;
import org.partypets.backend.repo.PartyRepo;
import org.partypets.backend.security.MongoUser;
import org.partypets.backend.security.MongoUserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class PartyServiceTest {

    PartyRepo partyRepo = mock(PartyRepo.class);
    UuIdService uuIdService = mock(UuIdService.class);

    MongoUserService mongoUserService = mock(MongoUserService.class);

    PartyService partyService = new PartyService(partyRepo, uuIdService, mongoUserService);


    @Test
    void expectListOfAllParties_whenGettingTheList() {
        //given
        Party newParty = new Party("abc", LocalDate.now(), "Home", "Dog-Bday", "user123");
        List<Party> expected = new ArrayList<>(List.of(newParty));
        //when
        when(partyRepo.findAll()).thenReturn(expected);
        List<Party> actual = partyService.list();
        //then
        assertEquals(expected, actual);
        verify(partyRepo).findAll();
    }

    @Test
    @WithMockUser(username="Henry")
    void expectId_whenAddedParty() {
        //given
        DTOParty newParty = new DTOParty(LocalDate.now(), "Home", "Dog-Bday");
        Party expected = new Party("abc", LocalDate.now(), "Home", "Dog-Bday", "user123");
        MongoUser user = new MongoUser("user123", "Henry", "Henry1");
        //when
        when(uuIdService.getRandomId()).thenReturn("abc");
        when(partyRepo.insert(expected)).thenReturn(expected);
        when(mongoUserService.getUserByUsername("Henry")).thenReturn(user);
        Party actual = partyService.add(newParty);
        //then
        assertEquals(expected, actual);
        verify(uuIdService).getRandomId();
        verify(partyRepo).insert(expected);
    }


    @Test
    void expectParty_whenGettingPartyDetails() {
        //given
        Party expected = new Party("abc", LocalDate.now(), "Home", "Dog-Bday", "user123");
        //when
        when(partyRepo.findById("abc")).thenReturn(Optional.of(expected));
        Party actual = partyService.getDetails("abc");
        //then
        assertEquals(expected, actual);
        verify(partyRepo).findById("abc");
    }

    @Test
    void expectUpdatedParty_whenEditingPartyDetails() {
        //given
        DTOParty dtoParty = new DTOParty(LocalDate.now(), "Home", "Dog-Bday");
        Party expected = new Party("abc", LocalDate.now(), "Home", "Dog-Bday", "user123");
        //when
        when(partyRepo.save(expected)).thenReturn(expected);
        Party actual = partyService.edit("abc", dtoParty);
        //then
        assertEquals(expected, actual);
        verify(partyRepo).save(expected);
    }

    @Test
    void expectDeleteMethodToBeCalled_whenDeletingParty() {
        //given
        String id = "abc";
        //when
        partyService.delete(id);
        //then
        verify(partyRepo).deleteById(id);
    }
}
