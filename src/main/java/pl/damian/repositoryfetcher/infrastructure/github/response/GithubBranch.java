package pl.damian.repositoryfetcher.infrastructure.github.response;

public record GithubBranch(String name, GithubCommit commit) { }
