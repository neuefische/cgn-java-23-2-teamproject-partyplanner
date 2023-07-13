package org.partypets.backend.controller;

import org.junit.jupiter.api.Test;
import org.partypets.backend.model.DTOParty;
import org.partypets.backend.service.PartyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.Date;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;


@SpringBootTest
@AutoConfigureMockMvc
class IntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PartyService partyService;

    @Test
    @DirtiesContext
    void expectPartyList_whenGettingAllParties() throws Exception {
        //Given
        DTOParty newParty = new DTOParty(LocalDate.now(), "Home", "Dog-Bday");
        this.partyService.add(newParty);
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
    @WithMockUser
    void expectNewPartyInList_whenPostingParty() throws Exception {
        String newParty = """
                {
                "date": "2035-01-01",
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

        mockMvc.perform(MockMvcRequestBuilders.post("/api/parties").content(newParty).contentType(MediaType.APPLICATION_JSON).with(csrf()))

                //Then
                .andExpect(MockMvcResultMatchers.content().json(expected)).andExpect(MockMvcResultMatchers.status().isOk());
    }


    @Test
    @DirtiesContext
    void expectParty_whenGettingByID() throws Exception {
        //Given
        DTOParty newParty = new DTOParty(LocalDate.now(), "Home", "Dog-Bday");
        this.partyService.add(newParty);
        String id = partyService.list().get(0).getId();
        String expected = """
                   
                        {
                            "id": "%s",
                            "location": "Home",
                            "theme": "Dog-Bday"
                         }
                    
                """.formatted(id);


        //When
        mockMvc.perform(MockMvcRequestBuilders.get("/api/parties/" + id))

                //Then
                .andExpect(MockMvcResultMatchers.content().json(expected)).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DirtiesContext
    @WithMockUser
    void expectUpdatedParty_whenPuttingParty() throws Exception {
        //Given
        DTOParty newParty = new DTOParty(LocalDate.now(), "Home", "Dog-Bday");
        this.partyService.add(newParty);
        String id = partyService.list().get(0).getId();
        String actual = """
                   
                        {
                            "date": "2035-01-01",
                            "location": "PawPalace",
                            "theme": "Party"
                         }
                    
                """;
        String expected = """
                   
                        {
                            "id": "%s",
                            "location": "PawPalace",
                            "theme": "Party"
                         }
                    
                """.formatted(id);


        //When
        mockMvc.perform(MockMvcRequestBuilders.put("/api/parties/" + id).content(actual).contentType(MediaType.APPLICATION_JSON).with(csrf()))

                //Then
                .andExpect(MockMvcResultMatchers.content().json(expected)).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DirtiesContext
    @WithMockUser
    void expectNoParty_whenDeletingParty() throws Exception {
        //Given
        DTOParty newParty = new DTOParty(LocalDate.now(), "Home", "Dog-Bday");
        this.partyService.add(newParty);
        String id = partyService.list().get(0).getId();
        String expected = """
                  []
                """;

        //When
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/parties/" + id).with(csrf()))
                .andExpect(MockMvcResultMatchers.status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.get("/api/parties"))

                //Then
                .andExpect(MockMvcResultMatchers.content().json(expected)).andExpect(MockMvcResultMatchers.status().isOk());
    }
    @Test
    @DirtiesContext
    void expectAnonymousUser_whenNotLoggedIn() throws Exception {
        String expected = "AnonymousUser";

        mockMvc.perform(MockMvcRequestBuilders.get("/api/user/me1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expected));
    }

    @Test
    @DirtiesContext
    @WithMockUser(username = "Henry")
    void expectUser_whenLoggedIn() throws Exception {
        String expected = "Henry";

        mockMvc.perform(MockMvcRequestBuilders.get("/api/user/me1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expected));
    }

    @Test
    @DirtiesContext
    @WithMockUser(username = "Henry")
    void expectAUser_whenLoggedIn() throws Exception {
        String expected = "Henry";

        mockMvc.perform(MockMvcRequestBuilders.get("/api/user/me2"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expected));
    }

    @Test
    @DirtiesContext
    void expectAnonymousUser_whenNotLoggedInOnMe2() throws Exception {
        String expected = "anonymousUser";

        mockMvc.perform(MockMvcRequestBuilders.get("/api/user/me2"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expected));
    }

    @Test
    @DirtiesContext
    void expectAnonymousUser_whenLoginWithoutUser() throws Exception {
        String expected = "anonymousUser";

        mockMvc.perform(MockMvcRequestBuilders.post("/api/user/login")
                        .with(csrf()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expected));
    }

    @Test
    @DirtiesContext
    @WithMockUser(username = "Henry")
    void expectUser_whenLoginWithUser() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/api/user/login")
                        .with(csrf()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Henry"));
    }

    @Test
    @DirtiesContext
    @WithMockUser(username = "Henry")
    void expectAnonymous_whenGettingMeAfterLogout() throws Exception {
        String expected = "anonymousUser";

        mockMvc.perform(MockMvcRequestBuilders.post("/api/user/login")
                        .with(csrf()))
                .andExpect(MockMvcResultMatchers.status().isOk());

        mockMvc.perform(MockMvcRequestBuilders.post("/api/user/logout")
                .with(csrf()));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/user/me2"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expected));
    }
}
