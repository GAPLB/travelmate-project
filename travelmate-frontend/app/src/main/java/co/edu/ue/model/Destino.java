package co.edu.ue.model;

<<<<<<< Updated upstream

import java.time.LocalDate;

public class Destino {
=======
/**
 * Modelo de Destino para el frontend Android.
 * Representa un destino asociado a un viaje.
 */
public class Destino {

>>>>>>> Stashed changes
    private Long id;
    private String nombre;
    private Double latitud;
    private Double longitud;
<<<<<<< Updated upstream
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
=======
    private String fechaVisita;   // Formato: YYYY-MM-DD
    private Long viajeId;         // ID del viaje al que pertenece

    // Constructor vacío (necesario para Gson/Retrofit)
    public Destino() {
    }

    public Destino(Long id, String nombre, Double latitud, Double longitud, String fechaVisita, Long viajeId) {
        this.id = id;
        this.nombre = nombre;
        this.latitud = latitud;
        this.longitud = longitud;
        this.fechaVisita = fechaVisita;
        this.viajeId = viajeId;
    }

    // Getters y Setters
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

    public String getFechaVisita() {
        return fechaVisita;
    }

    public void setFechaVisita(String fechaVisita) {
        this.fechaVisita = fechaVisita;
    }

    public Long getViajeId() {
        return viajeId;
    }

    public void setViajeId(Long viajeId) {
        this.viajeId = viajeId;
    }
}
>>>>>>> Stashed changes
