//package bench.artshop.order.security;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.ExpiredJwtException;
//import io.jsonwebtoken.Jwts;
//import org.springframework.http.HttpHeaders;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
//
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.stream.Collectors;
//
//public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
//    public JwtAuthorizationFilter(AuthenticationManager authenticationManager) {
//        super(authenticationManager);
//    }
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
//        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
//        if (token == null || !token.startsWith("Bearer ")) {
//            chain.doFilter(request, response);
//            return;
//        }
//        try {
//            Claims claims = Jwts.parser()
//                    .setSigningKey("password")
//                    .parseClaimsJws(token.replace("Bearer ", ""))
//                    .getBody();
//            String login = claims.getSubject();
//            String authorities = claims.get("authorities", String.class);
//            List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
//            if (authorities != null && !authorities.isEmpty()) {
//                grantedAuthorities = Arrays.stream(authorities.split(","))
//                        .map(SimpleGrantedAuthority::new)
//                        .collect(Collectors.toList());
//            }
//            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(login, null, grantedAuthorities);
//            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
//            chain.doFilter(request, response);
//        } catch (ExpiredJwtException e) {
//            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//        }
//
//
//    }
//}
