package com.projek.sipatik.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.projek.sipatik.models.Role;
import com.projek.sipatik.models.Users;
import com.projek.sipatik.repositories.UserRepository;
import com.projek.sipatik.security.JwtFilter;

import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/auth/**", "/auth-adm/**", "/", "/css/**", "/js/**", "/assets/**",
                                "/uploads/**", "/error/**")
                        .permitAll()
                        .requestMatchers("/api/nama-by-angkatan").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/user/**").hasRole("USER")
                        .requestMatchers("/api/**").hasAnyRole("USER", "ADMIN")
                        .anyRequest().authenticated())
                .exceptionHandling(ex -> ex
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            if (request.getRequestURI().startsWith("/api/")) {
                                // API endpoint - return JSON error
                                response.setContentType("application/json");
                                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                                response.getWriter().write("{\"timestamp\":\"" + LocalDateTime.now() +
                                        "\",\"status\":403,\"error\":\"Forbidden\",\"message\":\"Anda tidak memiliki akses ke halaman ini\",\"path\":\""
                                        +
                                        request.getRequestURI() + "\"}");
                            } else {
                                // Web endpoint - redirect to error page
                                response.sendRedirect("/error?status=403");
                            }
                        })
                        .authenticationEntryPoint((request, response, authException) -> {
                            if (request.getRequestURI().startsWith("/api/")) {
                                // API endpoint - return JSON error
                                response.setContentType("application/json");
                                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                                response.getWriter().write("{\"timestamp\":\"" + LocalDateTime.now() +
                                        "\",\"status\":401,\"error\":\"Unauthorized\",\"message\":\"Token tidak valid atau tidak ditemukan\",\"path\":\""
                                        +
                                        request.getRequestURI() + "\"}");
                            } else {
                                // Web endpoint - redirect to login
                                response.sendRedirect("/auth-adm/login");
                            }
                        })

                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    CommandLineRunner initAdmin(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (userRepository.findByEmail("ajengazzahra04@gmail.com").isEmpty()) {
                Users admin = new Users();
                admin.setNama("Bendahara Eksternal");
                admin.setEmail("ajengazzahra04@gmail.com");
                admin.setPassword(passwordEncoder.encode("Eks123")); // password default
                admin.setRole(Role.ADMIN);
                admin.setAngkatan(0L);

                userRepository.save(admin);
                System.out.println("=== Default admin created ===");
            }
        };
    }
}
