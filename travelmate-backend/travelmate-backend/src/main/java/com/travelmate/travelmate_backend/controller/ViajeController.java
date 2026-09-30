package com.travelmate.travelmate_backend.controller;

import com.travelmate.travelmate_backend.model.Viaje;
import com.travelmate.travelmate_backend.service.ViajeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/viaje")

public class ViajeController {

        @Autowired

        private ViajeService viajeService;

        // ---------- GET: listar todos los viajes de un usuario ----------
        // Ejemplo: GET /api/viajes/usuario/5  → lista los viajes del usuario con id 5
        @GetMapping("/usuario/{usuarioId}")
        public ResponseEntity<List<Viaje>> listarPorUsuario(@PathVariable Long usuarioId) {
            // @PathVariable toma el valor de {usuarioId} en la URL y lo pasa como parámetro
            List<Viaje> viajes = viajeService.listarPorUsuario(usuarioId);
            return ResponseEntity.ok(viajes);   // 200 OK + la lista en el body como JSON
        }

        // ---------- GET: obtener un viaje específico por su id ----------
        // Ejemplo: GET /api/viajes/3  → devuelve el viaje con id 3
        @GetMapping("/{id}")
        public ResponseEntity<Viaje> obtenerPorId(@PathVariable Long id) {
            return viajeService.buscarPorId(id)
                    .map(ResponseEntity::ok)              // si existe, 200 OK + el viaje
                    .orElse(ResponseEntity.notFound().build());  // si no existe, 404 Not Found
        }

        // ---------- POST: crear un nuevo viaje ----------
        // Ejemplo: POST /api/viajes
        @PostMapping
        public ResponseEntity<Viaje> crear(@RequestBody Viaje viaje) {
            // @RequestBody convierte el JSON recibido en un objeto Viaje automáticamente
            Viaje nuevoViaje = viajeService.crear(viaje);
            return ResponseEntity.status(201).body(nuevoViaje);   // 201 Created + el viaje ya guardado (con su id)
        }

        // ---------- PUT: actualizar un viaje existente ----------
        // Ejemplo: PUT /api/viajes/3
        @PutMapping("/{id}")
        public ResponseEntity<Viaje> actualizar(@PathVariable Long id, @RequestBody Viaje viaje) {
            try {
                Viaje viajeActualizado = viajeService.actualizar(id, viaje);
                return ResponseEntity.ok(viajeActualizado);   // 200 OK + el viaje ya actualizado
            } catch (RuntimeException e) {
                return ResponseEntity.notFound().build();   // si el id no existe, 404 Not Found
            }
        }

        // ---------- DELETE: eliminar un viaje ----------
        // Ejemplo: DELETE /api/viajes/3
        @DeleteMapping("/{id}")
        public ResponseEntity<Void> eliminar(@PathVariable Long id) {
            viajeService.eliminar(id);
            return ResponseEntity.noContent().build();   // 204 No Content: se eliminó, no hay nada que devolver
        }
    }

