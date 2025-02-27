package pl.damian.repositoryfetcher.model;

import pl.damian.repositoryfetcher.client.response.GithubBranch;
import pl.damian.repositoryfetcher.client.response.GithubRepository;

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
