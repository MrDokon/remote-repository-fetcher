package pl.damian.repositoryfetcher.model;

public record Repository(String name, String ownerLogin, boolean fork) { }
