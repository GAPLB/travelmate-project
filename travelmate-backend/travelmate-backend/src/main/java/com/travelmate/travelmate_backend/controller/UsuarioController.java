package com.travelmate.travelmate_backend.controller;

import com.travelmate.travelmate_backend.dto.LoginDTO;
import com.travelmate.travelmate_backend.dto.UsuarioDTO;
import com.travelmate.travelmate_backend.model.Usuario;
import com.travelmate.travelmate_backend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    // ---------- REGISTRO ----------
    @PostMapping("/registro")
    public ResponseEntity<?> registrar(@RequestBody UsuarioDTO dto) {

        // 1. Verificar que el email no esté ya registrado
        if (usuarioRepository.findByEmail(dto.getEmail()).isPresent()) {
            return ResponseEntity.status(409).body("El correo ya está registrado");
        }

        // 2. Crear la entidad Usuario a partir de los datos del DTO
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNombre(dto.getNombre());
        nuevoUsuario.setEmail(dto.getEmail());

        // 3. Encriptar la contraseña ANTES de guardarla
        nuevoUsuario.setPasswordHash(passwordEncoder.encode(dto.getPassword()));

        // 4. Guardar en la base de datos
        usuarioRepository.save(nuevoUsuario);

        return ResponseEntity.status(201).body("Usuario registrado con éxito");
    }
    // ---------- FIN REGISTRO ---------- (aquí SÍ se cierra el método, con su propia llave)


    // ---------- LOGIN ----------
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO dto) {

        // 1. Buscar al usuario por su email
        Usuario usuario = usuarioRepository.findByEmail(dto.getEmail())
                .orElse(null);

        // 2. Si no existe, no damos pistas de cuál dato falló
        if (usuario == null) {
            return ResponseEntity.status(401).body("Credenciales inválidas");
        }

        // 3. Comparar la contraseña ingresada contra el hash guardado
        boolean passwordCorrecta = passwordEncoder.matches(dto.getPassword(), usuario.getPasswordHash());

        if (!passwordCorrecta) {
            return ResponseEntity.status(401).body("Credenciales inválidas");
        }

        // 4. Login exitoso: devolvemos los datos básicos del usuario (sin el hash)
        return ResponseEntity.ok().body(new LoginResponse(usuario.getId(), usuario.getNombre(), usuario.getEmail()));
    }
    // ---------- FIN LOGIN ----------

    // Clase auxiliar para no devolver el objeto Usuario completo (evita exponer el passwordHash)
    public record LoginResponse(Long id, String nombre, String email) {}

} // ← cierre final de la clase UsuarioController