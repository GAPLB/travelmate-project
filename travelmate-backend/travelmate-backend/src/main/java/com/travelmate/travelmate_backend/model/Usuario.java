package com.travelmate.travelmate_backend.model;

import jakarta.persistence.*;

@Entity // le dice a Spring: "esta clase es una tabla"

    @Table(name = "usuarios")   // la conecta con tu tabla usuarios en PostgreSQL


    public class Usuario {

        @Id  // clave primaria

        @GeneratedValue(strategy = GenerationType.IDENTITY)  // el valor lo genera la BD (como tu BIGSERIAL)
        private long id;

        @Column(nullable = false)  // no puede quedar vacío, igual que tu NOT NULL en SQL
        private String nombre;

        @Column(nullable = false, unique = true)  // corresponde a tu UNIQUE NOT NULL del email
        private String email;

        @Column(name = "password_hash", nullable = false)  // el nombre de columna es distinto al del atributo Java
        private String passwordHash;

        public Usuario(){
            // Constructor vacío: JPA lo necesita internamente, aunque tú no lo uses directamente
        }

    // Getters y setters


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }
}
