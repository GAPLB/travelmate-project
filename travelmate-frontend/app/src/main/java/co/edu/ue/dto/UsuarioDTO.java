package co.edu.ue.dto;

<<<<<<< Updated upstream

public class UsuarioDTO {
    private String nombre;
    private String email;
    private String password;

    public UsuarioDTO() {

    }
=======
/**
 * DTO para enviar los datos de registro de un nuevo usuario al servidor.
 * Se envía como JSON en el body de la petición POST /api/usuarios/registro
 */
public class UsuarioDTO {

    private String nombre;
    private String email;
    private String password;   // El servidor la encripta con BCrypt

    // Constructor vacío (necesario para Gson)
    public UsuarioDTO() {
    }

>>>>>>> Stashed changes
    public UsuarioDTO(String nombre, String email, String password) {
        this.nombre = nombre;
        this.email = email;
        this.password = password;
    }

<<<<<<< Updated upstream
    // Getters y setters
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
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
=======
    // Getters y Setters
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
>>>>>>> Stashed changes
