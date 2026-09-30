package co.edu.ue.model;

<<<<<<< Updated upstream

public class Usuario {
    private long id;
    private String nombre;
    private String email;
    private String passwordHash;

    // Constructor vacío
    public Usuario() {

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
=======
/**
 * Modelo de Usuario para el frontend Android.
 * Representa los datos básicos del usuario que se reciben del servidor.
 */
public class Usuario {

    private long id;
    private String nombre;
    private String email;

    // Constructor vacío (necesario para Gson/Retrofit)
    public Usuario() {
    }

    public Usuario(long id, String nombre, String email) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
    }

    // Getters y Setters
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
}
>>>>>>> Stashed changes
