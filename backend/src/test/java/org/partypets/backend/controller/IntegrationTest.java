package org.partypets.backend.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.partypets.backend.security.MongoUser;
import org.partypets.backend.security.MongoUserDetailService;
import org.partypets.backend.security.MongoUserRepository;
import org.partypets.backend.service.PartyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;


@SpringBootTest
@AutoConfigureMockMvc
class IntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PartyService partyService;

    @Autowired
    private MongoUserRepository userRepository;


    private final MongoUserDetailService mongoUserDetailService = new MongoUserDetailService(userRepository);

    @Test
    @DirtiesContext
    void expectPartyList_whenGettingAllParties() throws Exception {
        //Given
        String expected = """
                    [
                        {
                            "date": "2035-01-01",
                            "location": "Home",
                            "theme": "Dog-Bday"
                        }
                    ]
                """;
        String loginData = """
                               
                        {
                            "username": "Henry",
                            "password": "Password"
                         }
                    
                """;
        String newParty = """
                {
                "date": "2035-01-01",
                "location": "Home",
                "theme": "Dog-Bday"
                }
                """;
        mockMvc.perform(MockMvcRequestBuilders.post("/api/user/register").content(loginData).contentType(MediaType.APPLICATION_JSON).with(csrf())).andExpect(MockMvcResultMatchers.status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.post("/api/user/login").with(httpBasic("Henry", "Password")).with(csrf())).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.content().string("Henry"));
        mockMvc.perform(MockMvcRequestBuilders.post("/api/parties").content(newParty).contentType(MediaType.APPLICATION_JSON).with(httpBasic("Henry", "Password")).with(csrf()));
        mockMvc.perform(MockMvcRequestBuilders.post("/api/user/logout").with(csrf()));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/user/me")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.content().string("anonymousUser"));

        //When
        mockMvc.perform(MockMvcRequestBuilders.get("/api/parties"))
                //Then
                .andExpect(MockMvcResultMatchers.content().json(expected)).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DirtiesContext
    @WithMockUser(username = "Henry", password = "Henry1")
    void expectNewPartyInList_whenPostingParty() throws Exception {
        MongoUser user = new MongoUser("user123", "Henry", "Henry1");
        userRepository.save(user);
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
        String loginData = """
                               
                        {
                            "username": "Henry",
                            "password": "Password"
                         }
                    
                """;
        String newParty = """
                {
                "date": "2035-01-01",
                "location": "Home",
                "theme": "Dog-Bday"
                }
                """;
        mockMvc.perform(MockMvcRequestBuilders.post("/api/user/register").content(loginData).contentType(MediaType.APPLICATION_JSON).with(csrf())).andExpect(MockMvcResultMatchers.status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.post("/api/user/login").with(httpBasic("Henry", "Password")).with(csrf())).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.content().string("Henry"));
        mockMvc.perform(MockMvcRequestBuilders.post("/api/parties").content(newParty).contentType(MediaType.APPLICATION_JSON).with(httpBasic("Henry", "Password")).with(csrf()));
        mockMvc.perform(MockMvcRequestBuilders.post("/api/user/logout").with(csrf()));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/user/me")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.content().string("anonymousUser"));

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
        String actual = """
                  
                        {
                            "date": "2035-01-01",
                            "location": "PawPalace",
                            "theme": "Party"
                         }
                    
                """;

        String loginData = """
                               
                        {
                            "username": "Henry",
                            "password": "Password"
                         }
                    
                """;
        String newParty = """
                {
                "date": "2035-01-01",
                "location": "Home",
                "theme": "Dog-Bday"
                }
                """;
        mockMvc.perform(MockMvcRequestBuilders.post("/api/user/register").content(loginData).contentType(MediaType.APPLICATION_JSON).with(csrf())).andExpect(MockMvcResultMatchers.status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.post("/api/user/login").with(httpBasic("Henry", "Password")).with(csrf())).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.content().string("Henry"));
        mockMvc.perform(MockMvcRequestBuilders.post("/api/parties").content(newParty).contentType(MediaType.APPLICATION_JSON).with(httpBasic("Henry", "Password")).with(csrf()));

        String id = partyService.list().get(0).getId();

        String expected = """
                   
                        {
                            "id": "%s",
                            "location": "PawPalace",
                            "theme": "Party"
                         }
                    
                """.formatted(id);

        //When
        mockMvc.perform(MockMvcRequestBuilders.put("/api/parties/" + id).content(actual).contentType(MediaType.APPLICATION_JSON).with(httpBasic("Henry", "Password")).with(csrf()))

                //Then
                .andExpect(MockMvcResultMatchers.content().json(expected)).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DirtiesContext
    void expectNoParty_whenDeletingParty() throws Exception {
        //Given
        String loginData = """
                               
                        {
                            "username": "Henry",
                            "password": "Password"
                         }
                    
                """;
        String newParty = """
                {
                "date": "2035-01-01",
                "location": "Home",
                "theme": "Dog-Bday"
                }
                """;
        mockMvc.perform(MockMvcRequestBuilders.post("/api/user/register").content(loginData).contentType(MediaType.APPLICATION_JSON).with(csrf())).andExpect(MockMvcResultMatchers.status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.post("/api/user/login").with(httpBasic("Henry", "Password")).with(csrf())).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.content().string("Henry"));
        mockMvc.perform(MockMvcRequestBuilders.post("/api/parties").content(newParty).contentType(MediaType.APPLICATION_JSON).with(httpBasic("Henry", "Password")).with(csrf()));

        String id = partyService.list().get(0).getId();

        String expected = """
                  []
                """;

        //When
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/parties/" + id).with(httpBasic("Henry", "Password")).with(csrf())).andExpect(MockMvcResultMatchers.status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.get("/api/parties"))

                //Then
                .andExpect(MockMvcResultMatchers.content().json(expected)).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DirtiesContext
    @WithMockUser(username = "Henry")
    void expectAUser_whenLoggedIn() throws Exception {
        String expected = "Henry";

        mockMvc.perform(MockMvcRequestBuilders.get("/api/user/me")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.content().string(expected));
    }

    @Test
    @DirtiesContext
    void expectAnonymousUser_whenNotLoggedInOnMe() throws Exception {
        String expected = "anonymousUser";

        mockMvc.perform(MockMvcRequestBuilders.get("/api/user/me")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.content().string(expected));
    }

    @Test
    @DirtiesContext
    void expectAnonymousUser_whenLoginWithoutUser() throws Exception {
        String expected = "anonymousUser";

        mockMvc.perform(MockMvcRequestBuilders.post("/api/user/login").with(csrf())).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.content().string(expected));
    }

    @Test
    @DirtiesContext
    @WithMockUser(username = "Henry")
    void expectUser_whenLoginWithUser() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/api/user/login").with(csrf())).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.content().string("Henry"));
    }

    @Test
    @DirtiesContext
    @WithMockUser(username = "Henry")
    void expectAnonymous_whenGettingMeAfterLogout() throws Exception {
        String expected = "anonymousUser";

        mockMvc.perform(MockMvcRequestBuilders.post("/api/user/login").with(csrf())).andExpect(MockMvcResultMatchers.status().isOk());

        mockMvc.perform(MockMvcRequestBuilders.post("/api/user/logout").with(csrf()));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/user/me")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.content().string(expected));
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

        mockMvc.perform(MockMvcRequestBuilders.post("/api/user/register").content(actual).contentType(MediaType.APPLICATION_JSON).with(csrf())).andExpect(MockMvcResultMatchers.status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.post("/api/user/login").with(httpBasic("Henry", "Password")).with(csrf())).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DirtiesContext
    void expectUserId_whenLoggedIn() throws Exception {
        String actual = """
                               
                        {
                            "username": "Franky",
                            "password": "Franky1"
                         }
                    
                """;

        mockMvc.perform(MockMvcRequestBuilders.post("/api/user/register").content(actual).contentType(MediaType.APPLICATION_JSON).with(csrf())).andExpect(MockMvcResultMatchers.status().isOk());

        Optional<MongoUser> user = userRepository.findByUsername("Franky");
        assert user.isPresent();
        String userId = user.get().id();
        mockMvc.perform(MockMvcRequestBuilders.post("/api/user/login").with(httpBasic("Franky", "Franky1")).with(csrf())).andExpect(MockMvcResultMatchers.status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.get("/api/user").with(httpBasic("Franky", "Franky1"))).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.content().string(userId));

    }

    @Test
    @DirtiesContext
    void expectNull_whenNotLoggedIn() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/user")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.content().string(""));

    }

    @AfterEach
    public void tearDown() {
        SecurityContextHolder.clearContext();
    }
}
