package pl.damian.repositoryfetcher.infrastructure.github.response;

public record GithubRepository(String name, GithubOwner owner, boolean fork) { }
