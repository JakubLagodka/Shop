package bench.artshop.order.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;
import org.zalando.problem.ThrowableProblem;

@ControllerAdvice
@Slf4j
public class AdviceController {
//    @ExceptionHandler(ResponseStatusException.class)
//    @ResponseStatus(HttpStatus.FORBIDDEN)
//    public void handleThrowableProblemException(ResponseStatusException e) {
//        log.error("Access denied! Try to login to get access!", e);
//    }
}