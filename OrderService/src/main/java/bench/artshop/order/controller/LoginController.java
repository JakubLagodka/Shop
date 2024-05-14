package bench.artshop.order.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import bench.artshop.order.dto.LoginDto;
import bench.artshop.order.dto.TokenDto;
import bench.artshop.order.service.LoginService;

@RestController
@RequestMapping(value = "/api/login", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class LoginController {
    private final LoginService loginService;

    @PostMapping
    public TokenDto login(@RequestBody LoginDto loginDto){
        return loginService.login(loginDto);
    }
}
