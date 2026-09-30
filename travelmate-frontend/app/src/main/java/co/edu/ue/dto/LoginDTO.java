package co.edu.ue.dto;

<<<<<<< Updated upstream

public class LoginDTO {
    private String email;
    private String password;

    public LoginDTO() {

    }
=======
/**
 * DTO para enviar las credenciales de login al servidor.
 * Se envía como JSON en el body de la petición POST /api/usuarios/login
 */
public class LoginDTO {

    private String email;
    private String password;

    // Constructor vacío (necesario para Gson)
    public LoginDTO() {
    }

>>>>>>> Stashed changes
    public LoginDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

<<<<<<< Updated upstream
    // Getters y setters
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
