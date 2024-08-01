package pl.damian.repositoryfetcher.controller.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import pl.damian.repositoryfetcher.model.Branch;
import pl.damian.repositoryfetcher.model.Repository;

import java.util.List;

public record RepositoryBranch(@JsonIgnoreProperties({"fork"}) Repository repository, List<Branch> branches) { }
