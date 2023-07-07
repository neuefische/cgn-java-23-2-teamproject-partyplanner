package org.partypets.backend.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class UuIdServiceTest {

    @Test
    void expectStringLengthGreater0_whenCalled() {

        UuIdService uuIdService = new UuIdService();
        String actual = uuIdService.getRandomId();

        assertTrue(actual.length() > 0);
    }
}
