package org.partypets.backend;

import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import java.io.IOException;

@SpringBootTest
class BackendApplicationTests {

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
	void contextLoads() {
		Assertions.assertTrue(true);
	}

	@AfterAll
	static void cleanUp() throws IOException {
		mockWebServer.close();
	}

}
