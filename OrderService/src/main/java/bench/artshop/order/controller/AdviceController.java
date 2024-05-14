package bench.artshop.order.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Slf4j
public class AdviceController {
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public void handleRuntimeException(RuntimeException e) {
        log.error("Access denied! Try to login to get access!", e);
    }
}