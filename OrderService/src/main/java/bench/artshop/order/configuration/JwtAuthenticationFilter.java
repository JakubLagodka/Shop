package bench.artshop.order.configuration;

import bench.artshop.order.repository.UserRepository;
import bench.artshop.order.service.JwtService;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.zalando.problem.ThrowableProblem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static bench.artshop.order.configuration.Constants.AUTHORIZATION;
import static bench.artshop.order.configuration.Constants.BEARER;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        final String authHeader = request.getHeader(AUTHORIZATION);

        try {
            if (!StringUtils.isEmpty(authHeader) && StringUtils.startsWithIgnoreCase(authHeader, BEARER)) {
                String token = authHeader.substring(7);
                List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
                if (jwtService.getAuthorities() != null && !jwtService.getAuthorities().isEmpty()) {
                    grantedAuthorities = Arrays.stream(jwtService.getAuthorities().split(","))
                            .map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toList());
                }
                String login = jwtService.extractLogin(token);
                if (StringUtils.isNotEmpty(login)
                        && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails userDetails = userRepository.userDetailsService()
                            .loadUserByUsername(login);
                    if (jwtService.isTokenValid(token, userDetails)) {
                        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                                login, null, grantedAuthorities);
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
                }
            }
            filterChain.doFilter(request, response);

        } catch (ThrowableProblem | JwtException e) {
            response.sendError(401, "We have problem with Your login, please try to login again to get access!");
        } catch (HttpMessageNotReadableException e) {
            response.sendError(400, "Required request body is missing!");
        }
    }
}