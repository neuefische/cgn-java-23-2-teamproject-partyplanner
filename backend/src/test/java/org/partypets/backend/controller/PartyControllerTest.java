package org.partypets.backend.controller;

import org.junit.jupiter.api.Test;
import org.partypets.backend.model.Diet;
import org.partypets.backend.model.Guest;
import org.partypets.backend.model.Party;
import org.partypets.backend.repo.PartyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class PartyControllerTest { // Integration Test: wie ein Fake postmann

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PartyRepo partyRepo;

    @Test
    @DirtiesContext
    void expectPartyList_whenGettingAllParties() throws Exception {
        //Given
        Party newParty = new Party("FakeDate", "Home", "Dog-Bday", List.of(new Guest("Gökhan", true, Diet.VEGETARIAN)));
        partyRepo.add(newParty);
        String expected = """
                    [
                        {
                            "id": "%s",
                            "date": "FakeDate",
                            "location": "Home",
                            "theme": "Dog-Bday",
                            "guests": [{"id":  "%s", "name":  "Gökhan", "rsvp":  true, "diet":  "VEGETARIAN"}]
                        }
                    ]
                """.formatted(newParty.getId(), newParty.getGuests().get(0).getId());


        //When
        mockMvc.perform(MockMvcRequestBuilders.get("/api/parties"))

                //Then
                .andExpect(MockMvcResultMatchers.content().json(expected));
    }


}