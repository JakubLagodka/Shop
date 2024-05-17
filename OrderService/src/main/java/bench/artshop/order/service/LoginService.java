package bench.artshop.order.service;

import bench.artshop.order.dto.LoginDto;
import bench.artshop.order.dto.TokenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static bench.artshop.order.configuration.Constants.*;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public TokenDto login(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getLogin(), loginDto.getPassword()));
        Map<String, Object> claims = new HashMap<>();
        String auth = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        claims.put(AUTHORITIES, auth);
        jwtService.setAuthorities(auth);
        return new TokenDto(jwtService.generateToken(claims, loginDto.getLogin()));
    }
}
