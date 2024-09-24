package pl.damian.repositoryfetcher.domain.model;

public record Repository(String name, String ownerLogin, boolean fork) { }
