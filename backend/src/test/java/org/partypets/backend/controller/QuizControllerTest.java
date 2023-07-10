package org.partypets.backend.controller;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.IOException;

@SpringBootTest
@AutoConfigureMockMvc
class QuizControllerTest {

    @Autowired
    MockMvc mockMvc;

    static MockWebServer mockWebServer;

    @BeforeAll
    static void setUp() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
    }

    @DynamicPropertySource
    static void backendProperties(DynamicPropertyRegistry registry) {
        registry.add("quiz-api.url", () -> mockWebServer.url("/").toString());
    }

    @Test
    void expectQuiz_whenGettingQuiz() throws Exception {
        //GIVEN
        String response = """
                {
                        "id": "abc",
                        "question": "The Platypus lays eggs. What animal species does it belong to? ",
                        "answers": [
                            {
                                "answerText": "Reptile",
                                "rightAnswer": false
                            },
                            {
                                "answerText": "Insect",
                                "rightAnswer": false
                            },
                            {
                                "answerText": "Mammal",
                                "rightAnswer": true
                            },
                            {
                                "answerText": "Amphibian",
                                "rightAnswer": false
                            }
                        ]
                    }
                """;
        mockWebServer.enqueue(new MockResponse()
                .setHeader("Content-Type", "application/json")
                .setBody(response));
        //WHEN
        mockMvc.perform(MockMvcRequestBuilders.get("/api/quiz"))
                //THEN
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Test
    void expectTrue_whenGetWithRightAnswer() throws Exception {
        //GIVEN
        String response = """
                {
                        "id": "abc",
                        "question": "The Platypus lays eggs. What animal species does it belong to? ",
                        "answers": [
                            {
                                "answerText": "Reptile",
                                "rightAnswer": false
                            },
                            {
                                "answerText": "Insect",
                                "rightAnswer": false
                            },
                            {
                                "answerText": "Mammal",
                                "rightAnswer": true
                            },
                            {
                                "answerText": "Amphibian",
                                "rightAnswer": false
                            }
                        ]
                    }
                """;
        String requestBody = """
                {
                        "answerText": "Mammal"
                    }
                """;
        mockWebServer.enqueue(new MockResponse()
                .setHeader("Content-Type", "application/json")
                .setBody(response));
        //WHEN
        mockMvc.perform(MockMvcRequestBuilders.get("/api/quiz/abc").content(requestBody).contentType(MediaType.APPLICATION_JSON))
                //THEN
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("true"));
    }

    @AfterAll
    static void cleanUp() throws IOException {
        mockWebServer.close();
    }
}
