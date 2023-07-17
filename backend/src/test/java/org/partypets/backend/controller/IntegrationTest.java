package org.partypets.backend.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.partypets.backend.model.DTOParty;
import org.partypets.backend.model.UuIdService;
import org.partypets.backend.repo.PartyRepo;
import org.partypets.backend.security.MongoUser;
import org.partypets.backend.security.MongoUserRepository;
import org.partypets.backend.security.MongoUserService;
import org.partypets.backend.service.PartyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.Collections;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;


@SpringBootTest
@AutoConfigureMockMvc
class IntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PartyService partyService;

    @Autowired
    private PartyRepo partyRepo;

    @Autowired
    private MongoUserRepository userRepository;

    @Test
    @DirtiesContext
    @WithMockUser
    void expectPartyList_whenGettingAllParties() throws Exception {
        //Given
        DTOParty newParty = new DTOParty(LocalDate.now(), "Home", "Dog-Bday");
        MongoUser user = new MongoUser("user123", "Henry", "Henry1");
        userRepository.save(user);
        Authentication auth = new UsernamePasswordAuthenticationToken("Henry", "Henry1");
        SecurityContextHolder.getContext().setAuthentication(auth);
        this.partyService.add(newParty);
        String expected = """
                    [
                        {
                            "location": "Home",
                            "theme": "Dog-Bday",
                            "userId": "user123"
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
        MongoUser user = new MongoUser("user123", "Henry", "Henry1");
        userRepository.save(user);
        Authentication auth = new UsernamePasswordAuthenticationToken("Henry", "Henry1");
        SecurityContextHolder.getContext().setAuthentication(auth);
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
                 "theme": "Dog-Bday",
                 "userId": "user123"
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
    @WithMockUser(username = "Henry")
    void expectAUser_whenLoggedIn() throws Exception {
        String expected = "Henry";

        mockMvc.perform(MockMvcRequestBuilders.get("/api/user/me"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expected));
    }

    @Test
    @DirtiesContext
    void expectAnonymousUser_whenNotLoggedInOnMe() throws Exception {
        String expected = "anonymousUser";

        mockMvc.perform(MockMvcRequestBuilders.get("/api/user/me"))
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

        mockMvc.perform(MockMvcRequestBuilders.get("/api/user/me"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expected));
    }

    @Test
    @DirtiesContext
    void expectLogin_whenClickingSubmitAfterRegister() throws Exception {
        String actual = """
                               
                        {
                            "username": "Henry",
                            "password": "Password"
                         }
                    
                """;

        mockMvc.perform(MockMvcRequestBuilders.post("/api/user/register").content(actual).contentType(MediaType.APPLICATION_JSON).with(csrf()))
                .andExpect(MockMvcResultMatchers.status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.post("/api/user/login").content(actual).contentType(MediaType.APPLICATION_JSON).with(csrf()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @AfterEach
    public void tearDown() {
        SecurityContextHolder.clearContext();
    }
}
