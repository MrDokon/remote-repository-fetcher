package pl.damian.repositoryfetcher.domain.model;

import pl.damian.repositoryfetcher.infrastructure.github.response.GithubBranch;
import pl.damian.repositoryfetcher.infrastructure.github.response.GithubRepository;

public class RemoteRepositoryModelMapper {

    private RemoteRepositoryModelMapper(){};

    public static Branch fromGithubBranchToBranch(GithubBranch githubBranch) {
        return new Branch(
                githubBranch.name(),
                githubBranch.commit().sha());
    }

    public static Repository fromGithubRepositoryToRepository(GithubRepository githubRepository) {
        return new Repository(
                githubRepository.name(),
                githubRepository.owner().login(),
                githubRepository.fork());
    }

}
