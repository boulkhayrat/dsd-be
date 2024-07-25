package com.kazyon.orderapi.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private final TokenAuthenticationFilter tokenAuthenticationFilter;
    private final IpAuthenticationFilter ipAuthenticationFilter;  // Add the IpAuthenticationFilter

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
                        .requestMatchers(HttpMethod.POST, "/api/orders").hasAnyAuthority(ADMIN, STORE, MANAGER)
                        .requestMatchers(HttpMethod.PUT, "/api/orders/**").hasAnyAuthority(ADMIN, STORE, MANAGER)
                        .requestMatchers(HttpMethod.POST,"/api/orderLines").hasAnyAuthority(ADMIN, STORE, MANAGER)
                        .requestMatchers(HttpMethod.PUT,"/api/orderLines/**").hasAnyAuthority(ADMIN, STORE, MANAGER)
                        .requestMatchers(HttpMethod.GET, "/api/users/me").hasAnyAuthority(ADMIN, STORE, MANAGER)
                        .requestMatchers("/api/orders", "/api/orders/**").hasAnyAuthority(ADMIN, MANAGER)
                        .requestMatchers("/api/orderLines", "/api/orderLines/**").hasAnyAuthority(ADMIN, MANAGER)
                        .requestMatchers("/api/users", "/api/users/**").hasAnyAuthority(ADMIN)
                        .requestMatchers("/static/public/**", "/auth/**").permitAll()
                        .requestMatchers("/uploads/**").permitAll() // Permit access to uploads directory
                        .requestMatchers("/", "/error", "/csrf", "/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs", "/v3/api-docs/**").permitAll()
                        .anyRequest().authenticated())
                .addFilterBefore(ipAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)  // Add IP filter
                .addFilterBefore(tokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)  // Add Token filter
                .exceptionHandling(exceptionHandling -> exceptionHandling.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public static final String ADMIN = "ADMIN";
    public static final String MANAGER = "MANAGER";
    public static final String STORE = "STORE";
}
