package bench.artshop.order.security;

import bench.artshop.order.config.JwtAuthenticationFilter;
import bench.artshop.order.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final UserRepository userRepository;
    @Autowired
    @Qualifier("customAuthenticationEntryPoint")
    AuthenticationEntryPoint authEntryPoint;
    //    @Bean
//    public UserDetailsService userDetailsService() {
//        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//        manager.createUser(User.withUsername("user")
//                .password(passwordEncoder().encode("userPass"))
//                .roles("USER")
//                .build());
//        manager.createUser(User.withUsername("admin")
//                .password(passwordEncoder().encode("adminPass"))
//                .roles("USER", "ADMIN")
//                .build());
//        return manager;
//    }
    public static final String[] AUTH_WHITELIST = {
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            "/v3/api-docs/**",
            "/api/public/**",
            "/api/public/authenticate",
            "/actuator/*",
            "/swagger-ui/**",
            "/api/login/**",
            "/api/order/**",
            "/api/user/**"
    };

    //    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
////        http.csrf(AbstractHttpConfigurer::disable)
////                .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry ->
////                        authorizationManagerRequestMatcherRegistry.requestMatchers(HttpMethod.DELETE).hasRole("ADMIN")
////                                .requestMatchers("/user/**").hasAnyRole("ADMIN")
////                                .requestMatchers("/order/**").hasAnyRole("USER", "ADMIN")
////                                .requestMatchers("/login/**").permitAll()
////                                .anyRequest().authenticated())
////                .httpBasic(Customizer.withDefaults())
////                .sessionManagement(httpSecuritySessionManagementConfigurer ->
////                        httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
//        http
////                .csrf(Customizer.withDefaults())
//                .authorizeHttpRequests(authorize -> authorize
//                        .anyRequest().permitAll())
//                .csrf(AbstractHttpConfigurer::disable)
//////                                .requestMatchers(HttpMethod.POST).permitAll()
////                                .requestMatchers(AUTH_WHITELIST).permitAll()
////                                .anyRequest().denyAll()
//////                                .anyRequest().authenticated()
////                )
//
////                .formLogin(Customizer.withDefaults());
////                .addFilterBefore(new SecurityFilter(), AuthorizationFilter.class)
////                .addFilter(new JwtAuthorizationFilter(authenticationManager(new AuthenticationConfiguration())))
//                .httpBasic(withDefaults());
//        return http.build();
//    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/api/user").permitAll()
                        .requestMatchers("/api/login").permitAll()
                        .requestMatchers(AUTH_WHITELIST).authenticated()
                        .anyRequest().authenticated())
//                .sessionManagement(manager -> manager.sessionCreationPolicy(STATELESS))
                .authenticationProvider(authenticationProvider()).addFilterBefore(
                        jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
//                .httpBasic(basic -> basic.authenticationEntryPoint(authEntryPoint))
//                .exceptionHandling(Customizer.withDefaults())
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(form -> form.failureHandler(authenticationFailureHandler())
                        .successHandler(authenticationSuccessHandler()))
                .exceptionHandling(ex -> ex.accessDeniedHandler(accessDeniedHandler()))
                .logout(Customizer.withDefaults());
        return http.build();

    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new CustomAuthenticationFailureHandler();
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new CustomAuthenticationSuccessHandler();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

    //    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//        return web -> web.ignoring().requestMatchers(AUTH_WHITELIST);
//    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userRepository.userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
            throws Exception {
        return config.getAuthenticationManager();
    }
}