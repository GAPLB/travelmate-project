package co.edu.ue;

public class RegistroRequest {
    private String nombre, email, password;
    public RegistroRequest(String nombre, String email, String password) {
        this.nombre = nombre; this.email = email; this.password = password;
    }
}