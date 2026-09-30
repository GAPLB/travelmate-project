package com.travelmate.travelmate_backend.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity// le dice a Spring: "esta clase representa una tabla"

@Table(name = "viajes")          // la conecta con tu tabla viajes en PostgreSQL

public class Viaje {

    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String titulo;

    private String descripcion;   // TEXT en la BD se mapea directo a String en Java, sin @Column especial

    @Column(name = "fecha_inicio")
    private LocalDate fechaInicio;   // LocalDate mapea directo al tipo DATE de PostgreSQL

    @Column(name = "fecha_fin")
    private LocalDate fechaFin;

    // Relación: cada viaje pertenece a UN usuario (relación muchos-a-uno: muchos viajes, un usuario)
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)  // esta es la columna FK en la tabla viajes
    private Usuario usuario;

    // Constructor vacío, necesario para JPA
    public Viaje() {}

    // Getters y setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}