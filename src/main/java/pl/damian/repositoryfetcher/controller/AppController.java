package pl.damian.repositoryfetcher.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.damian.repositoryfetcher.controller.dto.RepositoryBranch;
import pl.damian.repositoryfetcher.domain.VersionControlProvider;
import pl.damian.repositoryfetcher.domain.VersionControlService;
import reactor.core.publisher.Flux;

@RestController
public class AppController {

    private final VersionControlService service;

    public AppController(VersionControlService service) {
        this.service = service;
    }

    @GetMapping("/repositories/{username}")
    public Flux<RepositoryBranch> getUserRepositories(@PathVariable("username") String username,
                                                      @RequestParam(value = "includeForks", defaultValue = "false") boolean includeForks) {
        return service.getUserRepositoriesWithBranches(username, includeForks);
    }

}
