package org.partypets.backend.service;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.IOException;

@SpringBootTest
@AutoConfigureMockMvc
class QuizServiceTest {

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
                [
                    {
                        "id": "abc",
                        "question": "Does the Platypus lay eggs?",
                        "answer": "Yes"
                    }
                ]
                """;
        String expected = """
                {
                        "id": "abc",
                        "question": "Does the Platypus lay eggs?",
                        "answer": "Yes"
                    }
                """;
        mockWebServer.enqueue(new MockResponse()
                .setHeader("Content-Type", "application/json")
                .setBody(response));
        //WHEN
        mockMvc.perform(MockMvcRequestBuilders.get("/api/quiz"))
                //THEN
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(expected));
    }

    @AfterAll
    static void cleanUp() throws IOException {
        mockWebServer.close();
    }
}
