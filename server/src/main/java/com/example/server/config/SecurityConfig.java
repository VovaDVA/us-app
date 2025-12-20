package com.example.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)  // отключаем CSRF
                .authorizeHttpRequests(auth -> auth
                        // разрешаем доступ к Swagger UI и API
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        // разрешаем доступ ко всему остальному
                        .anyRequest().permitAll()
                )
                .httpBasic(AbstractHttpConfigurer::disable) // отключаем basic auth
                .formLogin(AbstractHttpConfigurer::disable); // отключаем форму логина

        return http.build();
    }
}
