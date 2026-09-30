package co.edu.ue.model;


import java.time.LocalDate;

public class Destino {
    private Long id;
    private String nombre;
    private Double latitud;
    private Double longitud;
    private LocalDate fechaVisita;
    private Viaje viaje;

    public Destino() {

    }

    // Getters y setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public Double getLatitud() {
        return latitud;
    }
    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }
    public Double getLongitud() {
        return longitud;
    }
    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }
    public LocalDate getFechaVisita() {
        return fechaVisita;
    }
    public void setFechaVisita(LocalDate fechaVisita) {
        this.fechaVisita = fechaVisita;
    }
    public Viaje getViaje() {
        return viaje;
    }
    public void setViaje(Viaje viaje) {
        this.viaje = viaje;
    }
}