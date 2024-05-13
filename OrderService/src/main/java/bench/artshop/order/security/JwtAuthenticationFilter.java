//package bench.artshop.order.security;
//
//import bench.artshop.order.dto.LoginDto;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import io.jsonwebtoken.impl.DefaultClaims;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.MediaType;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.AuthenticationServiceException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//import java.io.IOException;
//import java.util.Collections;
//import java.util.Date;
//import java.util.stream.Collectors;
//
//public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
//    private final ObjectMapper objectMapper;
//
//    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, ObjectMapper objectMapper) {
//        super(authenticationManager);
//        this.objectMapper = objectMapper;
//        setFilterProcessesUrl("/api/login");
//    }
//
//    @Override
//    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
//        if (!HttpMethod.POST.toString().equals(request.getMethod())) {
//            throw new AuthenticationServiceException("Not valid http method " + request.getMethod());
//        }
//        try {
//            LoginDto loginDto = objectMapper.readValue(request.getInputStream(), LoginDto.class);
//            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(loginDto.getLogin(), loginDto.getPassword());
//            return getAuthenticationManager().authenticate(usernamePasswordAuthenticationToken);
//        } catch (Exception e) {
//            throw new AuthenticationServiceException("Not valid json ", e);
//        }
//
//    }
//
//    @Override
//    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
//        Claims claims = new DefaultClaims()
//                .setSubject(authResult.getName())
//                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24));
//
//        claims.put("authorities", authResult.getAuthorities().stream()
//                .map(GrantedAuthority::getAuthority)
//                .collect(Collectors.joining(",")));
//
//        String token = Jwts.builder()
//                .setClaims(claims)
//                .signWith(SignatureAlgorithm.HS512, "password")
//                .compact();
//        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//        objectMapper.writeValue(response.getWriter(), Collections.singletonMap("token", token));
//    }
//}
