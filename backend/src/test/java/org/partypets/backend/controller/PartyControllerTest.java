package org.partypets.backend.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@AutoConfigureMockMvc

class PartyControllerTest { // Integration Test: wie ein Fake postmann

    @Autowired
    MockMvc mockMvc;

    @Test

    void test(){
        assertTrue(true);
    }


    //Given


    //When


    //Then

}