package org.partypets.backend.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.partypets.backend.exception.NoSuchPartyException;
import org.partypets.backend.exception.UsernameAlreadyExistsException;
import org.partypets.backend.model.Party;
import org.partypets.backend.model.PartyWithoutId;
import org.partypets.backend.model.UuIdService;
import org.partypets.backend.repo.PartyRepo;
import org.partypets.backend.security.MongoUser;
import org.partypets.backend.security.MongoUserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PartyServiceTest {

    PartyRepo partyRepo = mock(PartyRepo.class);
    UuIdService uuIdService = mock(UuIdService.class);

    MongoUserService mongoUserService = mock(MongoUserService.class);

    Authentication authentication = mock(Authentication.class);

    SecurityContext securityContext = mock(SecurityContext.class);

    PartyService partyService = new PartyService(partyRepo, uuIdService, mongoUserService);
    String username = "Henry";

    @BeforeEach
    void setUp() {
        when(authentication.getName()).thenReturn(username);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
    }

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
    void expectId_whenAddedParty() {
        //given
        PartyWithoutId newParty = new PartyWithoutId(LocalDate.now(), "Home", "Dog-Bday");
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
        verify(mongoUserService).getUserByUsername(username);
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
        PartyWithoutId partyWithoutId = new PartyWithoutId(LocalDate.now(), "Home", "Dog-Bday");
        Party expected = new Party("abc", LocalDate.now(), "Home", "Dog-Bday", "user123");
        MongoUser user = new MongoUser("user123", "Henry", "Henry1");
        //when
        when(partyRepo.findById("abc")).thenReturn(Optional.of(expected));
        when(partyRepo.save(expected)).thenReturn(expected);
        when(mongoUserService.getUserByUsername("Henry")).thenReturn(user);
        Party actual = partyService.edit("abc", partyWithoutId);
        //then
        assertEquals(expected, actual);
        verify(partyRepo).findById("abc");
        verify(partyRepo).save(expected);
        verify(mongoUserService).getUserByUsername(username);
    }

    @Test
    void expectDeleteMethodToBeCalled_whenDeletingParty() {
        //given
        String id = "abc";
        Party expected = new Party("abc", LocalDate.now(), "Home", "Dog-Bday", "user123");
        MongoUser user = new MongoUser("user123", "Henry", "Henry1");
        //when
        when(partyRepo.findById(id)).thenReturn(Optional.of(expected));
        when(mongoUserService.getUserByUsername("Henry")).thenReturn(user);
        partyService.delete(id);
        //then
        verify(partyRepo).findById(id);
        verify(partyRepo).deleteById(id);
        verify(mongoUserService).getUserByUsername(username);
    }

    @Test
    void expectNoSuchPartyException_whenCalledWithNonExistent() {
        //GIVEN
        String id = "abc";
        NoSuchPartyException exception = new NoSuchPartyException();
        //WHEN
        when(partyRepo.findById(id)).thenThrow(exception);
        //THEN
        assertThrows(RuntimeException.class, () -> partyService.getDetails(id));
        assertInstanceOf(RuntimeException.class, exception);
    }

    @Test
    void expectNoSuchPartyExceptionWithId_whenCalledWithNonExistent() {
        //GIVEN
        String id = "abc";
        NoSuchPartyException exception = new NoSuchPartyException(id);
        //WHEN
        when(partyRepo.findById(id)).thenThrow(exception);
        //THEN
        assertThrows(RuntimeException.class, () -> partyService.getDetails(id));
        assertInstanceOf(RuntimeException.class, exception);
        assertEquals("Party not found: abc", exception.getMessage());
    }

    @Test
    void testExceptionDefaultConstructor() {
        //GIVEN
        UsernameAlreadyExistsException exception = new UsernameAlreadyExistsException();
        //THEN
        assertNull(exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    void testExceptionConstructorWithMessageAndCause() {
        //GIVEN
        String message = "Username already exists!";
        Throwable cause = new RuntimeException("Some cause");
        UsernameAlreadyExistsException exception = new UsernameAlreadyExistsException(message, cause);
        //THEN
        assertEquals(message, exception.getMessage());
        assertEquals(cause, exception.getCause());
    }

    @Test
    void testExceptionConstructorWithCause() {
        //GIVEN
        Throwable cause = new RuntimeException("Some cause");
        UsernameAlreadyExistsException exception = new UsernameAlreadyExistsException(cause);
        //THEN
        assertEquals(cause, exception.getCause());
    }
}
