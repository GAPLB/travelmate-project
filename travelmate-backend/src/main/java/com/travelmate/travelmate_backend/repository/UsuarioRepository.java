package com.travelmate.travelmate_backend.repository;

import com.travelmate.travelmate_backend.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// JpaRepository<Usuario, Long> le da automáticamente métodos como save(), findById(), findAll()
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Spring Data JPA es capaz de generar la consulta SQL solo leyendo el nombre del método.
    // "findByEmail" → SELECT * FROM usuarios WHERE email = ?
    // No necesitas escribir el SQL tú mismo, ni implementar el método.


    Optional<Usuario> findByEmail(String email);   // antes: Optional<Object>
}
