package bench.artshop.order.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.zalando.problem.ThrowableProblem;

@ControllerAdvice
@Slf4j
public class AdviceController {
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public void handleAccessDeniedException(AccessDeniedException e) {
        log.error("Access denied! Try to login to get access!", e);
    }

    @ExceptionHandler(ThrowableProblem.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public void handleThrowableProblemException(ThrowableProblem e) {
        log.error("Access denied! Try to login to get access!", e);
    }
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        log.error("Bad request!", e);
    }
}