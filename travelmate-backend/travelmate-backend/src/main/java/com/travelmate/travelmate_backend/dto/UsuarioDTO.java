package com.travelmate.travelmate_backend.dto;

public class UsuarioDTO {

    private String nombre;

    private String email;

    private String password;   // aquí SÍ viaja en texto plano, porque el usuario la escribe así en el formulario
    // (se encripta DESPUÉS, dentro del servidor, nunca antes)


    // Constructor vacío, necesario para que Spring pueda "armar" el objeto desde el JSON recibido
    public UsuarioDTO() {

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

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}