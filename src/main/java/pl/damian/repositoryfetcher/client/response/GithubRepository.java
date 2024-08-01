package pl.damian.repositoryfetcher.client.response;

public record GithubRepository(String name, GithubOwner owner, boolean fork) { }
