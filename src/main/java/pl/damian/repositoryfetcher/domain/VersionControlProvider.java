package pl.damian.repositoryfetcher.domain;

import pl.damian.repositoryfetcher.domain.model.Branch;
import pl.damian.repositoryfetcher.domain.model.Repository;
import reactor.core.publisher.Flux;

public interface VersionControlProvider {

    Flux<Repository> getUserRepositories(String username);

    Flux<Branch> getUserBranchesForRepository(String username, String repository);
}
