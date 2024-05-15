package bench.artshop.order.controller;

import bench.artshop.order.dto.LoginDto;
import bench.artshop.order.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zalando.problem.ThrowableProblem;

@RestController
@RequestMapping(value = "/api/login", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class LoginController {
    private final LoginService loginService;

    @PostMapping
    public ResponseEntity<Object> login(@RequestBody LoginDto loginDto){
        try {
            return ResponseEntity.ok().body(loginService.login(loginDto));
        }catch (ThrowableProblem throwableProblem){
            return new ResponseEntity<>("User not found!: User with login = " + loginDto.getLogin() + " is no longer available", HttpStatusCode.valueOf(404));
        }
    }
}
