package com.aluralatam.forohub.infra;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {

    private final SecurityFilter securityFilter;
    private final String hashSecret;
    private final int bcryptStrength;

    public SecurityConfigurations(
            SecurityFilter securityFilter,
            @Value("${api.security.hash-secret}") String hashSecret,
            @Value("${api.security.bcrypt-strength}") int bcryptStrength) {
        this.securityFilter = securityFilter;
        this.hashSecret = hashSecret;
        this.bcryptStrength = bcryptStrength;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, "/login").permitAll()
                    .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/usuarios/**", "/cursos/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/usuarios/**", "/cursos/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/topics/**", "/comentarios/**").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.PUT, "/topics/**", "/comentarios/**").hasAnyRole("ADMIN", "USER")
                        .anyRequest().authenticated())
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder delegate = new BCryptPasswordEncoder(bcryptStrength);
        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence rawPassword) {
                return delegate.encode(rawPassword + hashSecret);
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                // Compatibilidad para contraseñas creadas antes de usar hash-secret.
                return delegate.matches(rawPassword + hashSecret, encodedPassword)
                        || delegate.matches(rawPassword, encodedPassword);
            }
        };
    }
}
