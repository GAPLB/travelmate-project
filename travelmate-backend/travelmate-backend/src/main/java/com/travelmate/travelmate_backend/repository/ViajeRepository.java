package com.travelmate.travelmate_backend.repository;

import com.travelmate.travelmate_backend.model.Viaje;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ViajeRepository extends JpaRepository<Viaje, Long> {


    // Spring Data JPA lee el nombre del método y genera la consulta SQL automáticamente:
    // "findByUsuarioId" → SELECT * FROM viajes WHERE usuario_id = ?
    // Esto nos sirve para listar solo los viajes DEL usuario que inició sesión, no de todos.

    List<Viaje> findByUsuarioId(Long usuarioId);
}


//Este método extra es importante: sin él, cualquier CRUD básico
// devolvería todos los viajes de todos los usuarios mezclados. Con findByUsuarioId,
// cada quien ve solo los suyos.