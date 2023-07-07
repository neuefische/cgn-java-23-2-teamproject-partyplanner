package org.partypets.backend.controller;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
class QuizControllerTest {

    @Autowired
    private MockMvc mockMvc;

    static MockWebServer mockWebServer;

    @Test
    void expectQuiz_whenGettingQuiz() throws Exception {
        mockWebServer = new MockWebServer();
        //GIVEN
        MockResponse response = new MockResponse().addHeader("Content-Type", "application/json");
        mockWebServer.enqueue(response);
        mockWebServer.start();
        //WHEN
        mockMvc.perform(MockMvcRequestBuilders.get("/api/quiz"))
        //THEN
                .andExpect(MockMvcResultMatchers.status().isOk());

        mockWebServer.close();
    }
}
