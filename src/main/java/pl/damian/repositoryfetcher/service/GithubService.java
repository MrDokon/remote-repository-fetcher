package pl.damian.repositoryfetcher.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import pl.damian.repositoryfetcher.client.GithubClient;
import pl.damian.repositoryfetcher.controller.dto.RepositoryBranch;
import reactor.core.publisher.Flux;

@Service
public class GithubService implements VersionControlService {

    private final GithubClient githubClient;

    public GithubService(GithubClient githubClient) {
        this.githubClient = githubClient;
    }

    @Cacheable(cacheNames = "UserRepositoriesWithBranches")
    public Flux<RepositoryBranch> getUserRepositoriesWithBranches(String username, boolean includeForks) {
        return githubClient
                .getUserRepositories(username)
                .filter(repository -> includeForks || !repository.fork())
                .flatMap(repository -> githubClient
                        .getUserBranchesForRepository(username, repository.name())
                        .collectList()
                        .map(branches -> new RepositoryBranch(repository, branches)));

    }

}
