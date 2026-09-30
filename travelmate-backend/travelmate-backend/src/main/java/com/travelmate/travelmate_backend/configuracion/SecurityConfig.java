package com.travelmate.travelmate_backend.configuracion;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity   // activa la configuración personalizada de seguridad de Spring

public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // Desactivamos CSRF: es una protección pensada para apps con formularios HTML tradicionales;
                // como tu backend es una API REST que consume una app móvil, no aplica de la misma forma.
                .csrf(csrf -> csrf.disable())

                // Definimos qué rutas son públicas y cuáles requieren autenticación
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/usuarios/registro").permitAll()  // cualquiera puede registrarse
                        .requestMatchers("/api/usuarios/login").permitAll()     // cualquiera puede intentar iniciar sesión
                        .anyRequest().permitAll()  // TEMPORAL: mientras no tengas login con JWT, deja todo abierto
                )

                // Sin sesiones de servidor: cada petición se valida por sí sola (típico en APIs REST)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );


        return http.build();
    }
}