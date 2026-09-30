package co.edu.ue.dto;

<<<<<<< Updated upstream

public class LoginResponse {
=======
/**
 * DTO para recibir la respuesta del servidor después de un login exitoso.
 * El servidor devuelve: { "id": 1, "nombre": "Juan", "email": "juan@email.com" }
 */
public class LoginResponse {

>>>>>>> Stashed changes
    private Long id;
    private String nombre;
    private String email;

<<<<<<< Updated upstream
    public LoginResponse() {

    }
=======
    // Constructor vacío (necesario para Gson)
    public LoginResponse() {
    }

>>>>>>> Stashed changes
    public LoginResponse(Long id, String nombre, String email) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
    }

<<<<<<< Updated upstream
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
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
}
=======
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
>>>>>>> Stashed changes
