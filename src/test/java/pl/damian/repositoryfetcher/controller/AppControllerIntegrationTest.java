package pl.damian.repositoryfetcher.controller;

import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.client.WireMock.status;

@WireMockTest(httpPort = 8282)
@SpringBootTest
@AutoConfigureWebTestClient
class AppControllerIntegrationTest {

    private static final String USERNAME = "MrDokon";

    @Autowired
    private WebTestClient webClient;

    @Test
    void shouldGetUserRepositoriesWithBranchesWithoutForksByDefault() {
        webClient.get().uri("/repositories/{username}", USERNAME)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.length()").isEqualTo(2)
                .jsonPath("$[?(@.repository.name == 'cars-app')].branches.length()").isEqualTo(2)
                .jsonPath("$..repository[?(@.ownerLogin == 'MrDokon')]").exists()
                .jsonPath("$..branches[?(@.name == 'new-mock-branch')]").exists()
                .jsonPath("$..branches[?(@.lastCommitSha == '3440a916f24d949fdc04c684250cdcaa5455b703')]").exists();
    }

    @Test
    void shouldGetUserRepositoriesWithBranchesWithForksIncluded() {
        webClient.get().uri("/repositories/{username}?includeForks=true", USERNAME)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.length()").isEqualTo(3)
                .jsonPath("$..repository[?(@.name == 'java-design-patterns')]").exists();
    }

    @Test
    void shouldReturnNotFoundErrorIfClientNotFoundException() {
        stubFor(get(urlPathMatching("/users/[a-zA-Z0-9]+/repos"))
                .willReturn(status(404)
                        .withHeader("Content-Type", "application/json")));

        webClient.get().uri("/repositories/{username}", USERNAME)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isEqualTo(HttpStatus.NOT_FOUND)
                .expectBody()
                .json("{\"status\":404,\"message\":\"Not Found - The specified user probably does not exist.\"}");
    }
    // TODO to consider - connection & response timeout tests
}