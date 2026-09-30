package com.travelmate.travelmate_backend.model;
import com.travelmate.travelmate_backend.model.Viaje;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "destinos")
public class Destino {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String nombre;

    private Double latitud;    // corresponde a DOUBLE PRECISION en tu tabla, opcional

    private Double longitud;   // opcional también

    @Column(name = "fecha_visita")
    private LocalDate fechaVisita;

    // Relación: cada destino pertenece a UN viaje (muchos destinos, un viaje)
    @ManyToOne
    @JoinColumn(name = "viaje_id", nullable = false)
    private Viaje viaje;

    public Destino() {}

    // Getters y setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public Double getLatitud() { return latitud; }
    public void setLatitud(Double latitud) { this.latitud = latitud; }

    public Double getLongitud() { return longitud; }
    public void setLongitud(Double longitud) { this.longitud = longitud; }

    public LocalDate getFechaVisita() { return fechaVisita; }
    public void setFechaVisita(LocalDate fechaVisita) { this.fechaVisita = fechaVisita; }

    public Viaje getViaje() { return viaje; }
    public void setViaje(Viaje viaje) { this.viaje = viaje; }
}