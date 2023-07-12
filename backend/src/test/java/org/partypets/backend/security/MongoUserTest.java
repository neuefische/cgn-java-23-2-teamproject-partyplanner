package org.partypets.backend.security;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MongoUserTest {
    private MongoUser user;

    @BeforeEach
    void setUp() {
        user = new MongoUser("abc", "Henry", "p4ssw0rd");
    }

    @Test
    void id() {
        assertEquals("abc", this.user.id());
    }

    @Test
    void username() {
        assertEquals("Henry", this.user.username());
    }

    @Test
    void password() {
        assertEquals("p4ssw0rd", this.user.password());
    }
}