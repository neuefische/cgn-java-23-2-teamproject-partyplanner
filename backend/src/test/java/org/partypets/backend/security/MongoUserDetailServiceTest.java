package org.partypets.backend.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MongoUserDetailServiceTest {

    private final MongoUserRepository userRepository = mock(MongoUserRepository.class);
    private MongoUserDetailService userDetailService = new MongoUserDetailService(userRepository);

    @Test
    void loadUserByUsername() {
        //GIVEN
        MongoUser expected = new MongoUser("abc", "Henry", "p4ssw0rd");
        String username = "Henry";
        //WHEN
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(expected));
        UserDetails actual = userDetailService.loadUserByUsername("Henry");
        //THEN
        assertEquals(expected.username(), actual.getUsername());
        verify(userRepository).findByUsername(username);
    }

    @Test
    void expectUserWithoutPassword() {
        //GIVEN
        MongoUser expected = new MongoUser("abc", "Henry", "p4ssw0rd");
        String username = "Henry";
        //WHEN
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(expected));
        UserWithoutPassword actual = userDetailService.getUserWithoutPassword(username);
        //THEN
        assertEquals(expected.id(), actual.id());
        verify(userRepository).findByUsername(username);
    }
}
