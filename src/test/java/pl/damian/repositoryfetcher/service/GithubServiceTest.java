package pl.damian.repositoryfetcher.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.damian.repositoryfetcher.client.GithubClient;
import pl.damian.repositoryfetcher.controller.dto.RepositoryBranch;
import pl.damian.repositoryfetcher.model.Branch;
import pl.damian.repositoryfetcher.model.Repository;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GithubServiceTest {

    @Mock
    private GithubClient githubClient;

    @InjectMocks
    private GithubService githubService;

    private Flux<Branch> branches;
    private Flux<Repository> repositories;
    private RepositoryBranch forkedRepositoryWithBranches;
    private RepositoryBranch notForkedRepositoryWithBranches;
    private static final String USERNAME = "user1";

    @Test
    void shouldReturnUserRepositoryWithBranchesIfNoForksIncluded() {
        prepareTestData();
        when(githubClient.getUserRepositories(any())).thenReturn(repositories);
        when(githubClient.getUserBranchesForRepository(any(), any())).thenReturn(branches);

        StepVerifier
                .create(githubService.getUserRepositoriesWithBranches(USERNAME, false))
                .expectNext(notForkedRepositoryWithBranches)
                .verifyComplete();
    }

    @Test
    void shouldReturnUserRepositoriesWithBranchesIfForksIncluded() {
        prepareTestData();
        when(githubClient.getUserRepositories(any())).thenReturn(repositories);
        when(githubClient.getUserBranchesForRepository(any(), any())).thenReturn(branches);

        StepVerifier
                .create(githubService.getUserRepositoriesWithBranches(USERNAME, true))
                .expectNext(notForkedRepositoryWithBranches)
                .expectNext(forkedRepositoryWithBranches)
                .verifyComplete();
    }

    @Test
    void shouldReturnEmptyFluxIfUserWithoutRepositories() {
        when(githubClient.getUserRepositories(any())).thenReturn(Flux.empty());

        StepVerifier
                .create(githubService.getUserRepositoriesWithBranches(USERNAME, false))
                .verifyComplete();
    }

    private void prepareTestData() {
        var notForkedRepository = new Repository("no-forked-repo","owner-login-nfr", false);
        var forkedRepository = new Repository("forked-repo","owner-login-fr", true);
        repositories = Flux.just(notForkedRepository, forkedRepository);

        var firstBranch = new Branch("branch-name-1","sha25ac21e32a8550a5b03d19084067603e1a5ff");
        var secondBranch = new Branch("branch-name-2", "shad0b74bc8bb269fec35ad4f3b23b0092101e56");
        branches = Flux.just(firstBranch, secondBranch);

        notForkedRepositoryWithBranches = new RepositoryBranch(notForkedRepository, List.of(firstBranch, secondBranch));
        forkedRepositoryWithBranches = new RepositoryBranch(forkedRepository, List.of(firstBranch, secondBranch));
    }

}