package org.partypets.backend.controller;

import org.junit.jupiter.api.Test;
import org.partypets.backend.model.Party;
import org.partypets.backend.repo.PartyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Date;
import java.util.List;


@SpringBootTest
@AutoConfigureMockMvc
class PartyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PartyRepo partyRepo;

    @Test
    @DirtiesContext
    void expectPartyList_whenGettingAllParties() throws Exception {
        //Given
        Party newParty = new Party(null, new Date(), "Home", "Dog-Bday");
        this.partyRepo.setParties(List.of(newParty));
        String expected = """
                    [
                        {
                            "location": "Home",
                            "theme": "Dog-Bday"
                         }
                    ]
                """;


        //When
        mockMvc.perform(MockMvcRequestBuilders.get("/api/parties"))

                //Then
                .andExpect(MockMvcResultMatchers.content().json(expected)).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DirtiesContext
    void expectNewPartyInList_whenPostingParty() throws Exception {
        String newParty = """
                {
                    "location": "Home",
                    "theme": "Dog-Bday"
                }
                """;

        String expected = """
                [
                {
                "location": "Home",
                 "theme": "Dog-Bday"
                }
                ]
                """;

        mockMvc.perform(MockMvcRequestBuilders.post("/api/parties").content(newParty).contentType(MediaType.APPLICATION_JSON))

                //Then
                .andExpect(MockMvcResultMatchers.content().json(expected)).andExpect(MockMvcResultMatchers.status().isOk());
    }
}



