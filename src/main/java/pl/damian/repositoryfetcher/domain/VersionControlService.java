package pl.damian.repositoryfetcher.domain;

import org.springframework.cache.annotation.Cacheable;
import pl.damian.repositoryfetcher.controller.dto.RepositoryBranch;
import reactor.core.publisher.Flux;

public class VersionControlService {

    private final VersionControlProvider versionControlProvider;

    public VersionControlService(VersionControlProvider versionControlProvider) {
        this.versionControlProvider = versionControlProvider;
    }

    @Cacheable(cacheNames = "UserRepositoriesWithBranches")
    public Flux<RepositoryBranch> getUserRepositoriesWithBranches(String username, boolean includeForks) {
        return versionControlProvider.getUserRepositories(username)
                .filter(repository -> includeForks || !repository.fork())
                .flatMap(repository -> versionControlProvider.getUserBranchesForRepository(username, repository.name())
                        .collectList()
                        .map(branches -> new RepositoryBranch(repository, branches)));
    }

}
