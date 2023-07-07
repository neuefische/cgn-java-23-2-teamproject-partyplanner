package org.partypets.backend.controller;

import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.partypets.backend.model.DTOParty;
import org.partypets.backend.service.PartyService;
import org.partypets.backend.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.reactive.function.client.WebClient;

import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.Date;


@SpringBootTest
@AutoConfigureMockMvc
class PartyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PartyService partyService;

    static MockWebServer mockWebServer;

    @BeforeAll
    static void setUp() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
    }

    @DynamicPropertySource
    static void backendProperties(DynamicPropertyRegistry registry) {
        registry.add("quiz-api.url", () -> mockWebServer.url("/quiz").toString());
    }

    @Test
    @DirtiesContext
    void expectPartyList_whenGettingAllParties() throws Exception {
        //Given
        DTOParty newParty = new DTOParty(new Date(), "Home", "Dog-Bday");
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


    @Test
    @DirtiesContext
    void expectParty_whenGettingByID() throws Exception {
        //Given
        DTOParty newParty = new DTOParty(new Date(), "Home", "Dog-Bday");
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
    void expectUpdatedParty_whenPuttingParty() throws Exception {
        //Given
        DTOParty newParty = new DTOParty(new Date(), "Home", "Dog-Bday");
        this.partyService.add(newParty);
        String id = partyService.list().get(0).getId();
        String actual = """
                   
                        {
                            "id": "%s",
                            "location": "PawPalace",
                            "theme": "Party"
                         }
                    
                """.formatted(id);
        String expected = """
                   
                        {
                            "id": "%s",
                            "location": "PawPalace",
                            "theme": "Party"
                         }
                    
                """.formatted(id);


        //When
        mockMvc.perform(MockMvcRequestBuilders.put("/api/parties/" + id).content(actual).contentType(MediaType.APPLICATION_JSON))

                //Then
                .andExpect(MockMvcResultMatchers.content().json(expected)).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DirtiesContext
    void expectNoParty_whenDeletingParty() throws Exception {
        //Given
        DTOParty newParty = new DTOParty(new Date(), "Home", "Dog-Bday");
        this.partyService.add(newParty);
        String id = partyService.list().get(0).getId();
        String expected = """
                  []
                """;

        //When
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/parties/" + id))
                .andExpect(MockMvcResultMatchers.status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.get("/api/parties"))

                //Then
                .andExpect(MockMvcResultMatchers.content().json(expected)).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @AfterAll
    static void cleanUp() throws IOException {
        mockWebServer.close();
    }
}
