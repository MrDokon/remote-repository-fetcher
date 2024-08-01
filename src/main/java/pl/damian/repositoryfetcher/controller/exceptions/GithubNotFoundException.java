package pl.damian.repositoryfetcher.controller.exceptions;

import java.io.Serial;

public class GithubNotFoundException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1;

    public GithubNotFoundException(String message) {
        super(message);
    }

}
