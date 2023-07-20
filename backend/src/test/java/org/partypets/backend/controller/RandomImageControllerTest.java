package org.partypets.backend.controller;

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
class RandomImageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private static MockWebServer mockWebServer;

    @BeforeAll
    static void beforeAll() throws Exception {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
    }

    @DynamicPropertySource
    static void backendProperties(DynamicPropertyRegistry registry) {
        registry.add("randomCatImage-api.url", () -> mockWebServer.url("/").toString());
    }

    @Test
    void expectImage_whenGetRandomImageAPI() throws Exception {

        String response = """
        {
            "id": "21NRDbMJF94",
            "alt_description": "short-fur brown and white cat resting on floor",
            "urls": {
                "small": "image"
            }
        }
        """;

        String expected = """
        {
            "id": "21NRDbMJF94",
            "alt_description": "short-fur brown and white cat resting on floor",
            "urls": {
                "small": "image"
            }
        }
        """;

        mockWebServer.enqueue(new MockResponse()
                .setHeader("Content-Type", "application/json")
                .setBody(response));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/randomCatImage"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(expected))
        ;
    }

    @AfterAll
    static void afterAll() throws IOException {
        mockWebServer.shutdown();
    }
}
