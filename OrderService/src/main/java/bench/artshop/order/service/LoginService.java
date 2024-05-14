package bench.artshop.order.service;

import bench.artshop.order.dto.LoginDto;
import bench.artshop.order.dto.TokenDto;
import bench.artshop.order.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static bench.artshop.order.util.ProblemUtils.getUserWithGivenLoginNotFoundProblem;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public TokenDto login(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getLogin(), loginDto.getPassword()));
        var user = userRepository.findByLogin(loginDto.getLogin())
                .orElseThrow(() -> getUserWithGivenLoginNotFoundProblem("Invalid email or password"));
        var jwt = jwtService.generateToken(user);
        Map<String,String> claims = new HashMap<>();
        claims.put("authorities", authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(",")));
        String token = Jwts.builder().setClaims(claims).setSubject(loginDto.getLogin())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(jwtService.getSigningKey(), SignatureAlgorithm.HS256).compact();

        return new TokenDto(token);
    }
}
