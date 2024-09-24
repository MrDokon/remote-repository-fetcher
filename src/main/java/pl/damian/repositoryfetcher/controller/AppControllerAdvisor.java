package pl.damian.repositoryfetcher.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.damian.repositoryfetcher.controller.exceptions.GithubNotFoundException;
import pl.damian.repositoryfetcher.domain.model.ErrorObject;

@ControllerAdvice
public class AppControllerAdvisor {

    @ExceptionHandler(value = GithubNotFoundException.class)
    public ResponseEntity<ErrorObject> handleNotFoundException(GithubNotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorObject(HttpStatus.NOT_FOUND.value(), exception.getMessage()));
    }

}