package bench.artshop.order.service;

import bench.artshop.order.dto.LoginDto;
import bench.artshop.order.dto.TokenDto;
import bench.artshop.order.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public TokenDto login(LoginDto loginDto) {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.getLogin(), loginDto.getPassword()));
            var user = userRepository.findByLogin(loginDto.getLogin())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));
            var jwt = jwtService.generateToken(user);
            return new TokenDto(jwt);
        }
}
