package pl.damian.repositoryfetcher.service;

import pl.damian.repositoryfetcher.controller.dto.RepositoryBranch;
import reactor.core.publisher.Flux;

public interface VersionControlService {

    Flux<RepositoryBranch> getUserRepositoriesWithBranches(String username, boolean includeForks);

}
