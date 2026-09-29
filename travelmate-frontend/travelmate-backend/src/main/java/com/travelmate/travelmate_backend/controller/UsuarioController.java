package com.travelmate.travelmate_backend.controller;

import com.travelmate.travelmate_backend.dto.UsuarioDTO;
import com.travelmate.travelmate_backend.model.Usuario;
import com.travelmate.travelmate_backend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController                    // le dice a Spring: "esta clase responde peticiones HTTP y devuelve JSON"
@RequestMapping("/api/usuarios")   // todas las rutas de esta clase empiezan con /api/usuarios
public class UsuarioController {

    @Autowired                      // Spring "inyecta" automáticamente una instancia lista para usar
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;   // el BCrypt que registraste en SecurityConfig

    @PostMapping("/registro")       // esta función responde a POST /api/usuarios/registro
    public ResponseEntity<?> registrar(@RequestBody UsuarioDTO dto) {
        // @RequestBody convierte automáticamente el JSON que llega en el body
        // en un objeto UsuarioDTO con sus campos ya llenos

        // 1. Verificar que el email no esté ya registrado
        if (usuarioRepository.findByEmail(dto.getEmail()).isPresent()) {
            // 409 = Conflict, código HTTP estándar para "ya existe"
            return ResponseEntity.status(409).body("El correo ya está registrado");
        }

        // 2. Crear la entidad Usuario a partir de los datos del DTO
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNombre(dto.getNombre());
        nuevoUsuario.setEmail(dto.getEmail());

        // 3. Aquí ocurre la magia: encriptamos la contraseña ANTES de guardarla
        nuevoUsuario.setPasswordHash(passwordEncoder.encode(dto.getPassword()));

        // 4. Guardar en la base de datos (INSERT automático gracias a JpaRepository)
        usuarioRepository.save(nuevoUsuario);

        // 201 = Created, código HTTP estándar para "se creó el recurso correctamente"
        return ResponseEntity.status(201).body("Usuario registrado con éxito");
    }
}

