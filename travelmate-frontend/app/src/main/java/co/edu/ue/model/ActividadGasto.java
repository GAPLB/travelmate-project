package co.edu.ue.model;


import java.math.BigDecimal;
import java.time.LocalDate;

public class ActividadGasto {
    private Long id;
    private String concepto;
    private BigDecimal monto;
    private String categoria;
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