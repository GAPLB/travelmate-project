package co.edu.ue.model;

<<<<<<< Updated upstream

import java.math.BigDecimal;
import java.time.LocalDate;

public class ActividadGasto {
=======
import java.math.BigDecimal;

/**
 * Modelo de ActividadGasto para el frontend Android.
 * Representa un gasto o actividad asociada a un viaje.
 */
public class ActividadGasto {

>>>>>>> Stashed changes
    private Long id;
    private String concepto;
    private BigDecimal monto;
    private String categoria;
<<<<<<< Updated upstream
    private LocalDate fecha;
    private Viaje viaje;

    public ActividadGasto() {

    }

    // Getters y setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getConcepto() {
        return concepto;
    }
    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }
    public BigDecimal getMonto() {
        return monto;
    }
    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }
    public String getCategoria() {
        return categoria;
    }
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    public LocalDate getFecha() {
        return fecha;
    }
    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
    public Viaje getViaje() {
        return viaje;
    }
    public void setViaje(Viaje viaje) {
        this.viaje = viaje;
    }
}
=======
    private String fecha;         // Formato: YYYY-MM-DD
    private Long viajeId;         // ID del viaje al que pertenece

    // Constructor vacío (necesario para Gson/Retrofit)
    public ActividadGasto() {
    }

    public ActividadGasto(Long id, String concepto, BigDecimal monto, String categoria, String fecha, Long viajeId) {
        this.id = id;
        this.concepto = concepto;
        this.monto = monto;
        this.categoria = categoria;
        this.fecha = fecha;
        this.viajeId = viajeId;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Long getViajeId() {
        return viajeId;
    }

    public void setViajeId(Long viajeId) {
        this.viajeId = viajeId;
    }
}
>>>>>>> Stashed changes
