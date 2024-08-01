package pl.damian.repositoryfetcher.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import pl.damian.repositoryfetcher.client.response.GithubBranch;
import pl.damian.repositoryfetcher.client.response.GithubRepository;
import pl.damian.repositoryfetcher.controller.exceptions.GithubNotFoundException;
import pl.damian.repositoryfetcher.model.Branch;
import pl.damian.repositoryfetcher.model.RemoteRepositoryModelMapper;
import pl.damian.repositoryfetcher.model.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class GithubClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(GithubClient.class);
    private final WebClient webClient;

    public GithubClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public Flux<Repository> getUserRepositories(String username) {
        return get("users/{username}/repos",
                GithubRepository.class,
                username)
                .mapNotNull(RemoteRepositoryModelMapper::fromGithubRepositoryToRepository);
    }

    public Flux<Branch> getUserBranchesForRepository(String username, String repository) {
        return get("repos/{username}/{repository}/branches",
                GithubBranch.class,
                username,
                repository)
                .mapNotNull(RemoteRepositoryModelMapper::fromGithubBranchToBranch);
    }

    private <T> Flux<T> get(String url, Class<T> clazz, Object... objects) {
        return webClient
                .get()
                .uri(url, objects)
                .retrieve()
                .onStatus(HttpStatus.NOT_FOUND::equals, response -> {
                    LOGGER.error(String.format("404 Not Found: %s", response.request().getURI()));
                    return Mono.error(new GithubNotFoundException("Not Found - The specified user probably does not exist."));
                })
                .bodyToFlux(clazz);
    }

}
