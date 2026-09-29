package com.travelmate.travelmate_backend.configuracion;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.net.PasswordAuthentication;

@Configuration // le dice a Spring: "esta clase define configuraciones/herramientas para todo el proyecto"
public class SecurityConfig {
    @Bean  // registra este objeto para que Spring lo entregue donde se lo pidan
    public PasswordEncoder passwordEncoder(){
        // BCryptPasswordEncoder es el algoritmo que convierte "1234" en algo como
        // "$2a$10$N9qo8uLOickgx2ZMRZoMy..." — irreversible, no se puede "desencriptar"

        return new BCryptPasswordEncoder();
    }
}
