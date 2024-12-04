package com.example.tocloc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Desativa CSRF, necessário para o console H2
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // Permite acesso irrestrito ao console H2
                        .requestMatchers("/h2-console/**").permitAll()
                        // Permite todas as outras requisições sem autenticação
                        .anyRequest().permitAll()
                )
                // Permite o uso do H2 Console em um iframe da mesma origem
                .headers(headers -> headers
                        .disable()  // Desativa todas as configurações de cabeçalhos, permitindo o iframe
                );

        return http.build();
    }
}
