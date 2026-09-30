package com.travelmate.travelmate_backend.controller;

import com.travelmate.travelmate_backend.model.Destino;
import com.travelmate.travelmate_backend.service.DestinoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

@RequestMapping("/api/destinos")

public class DestinoController {

    @Autowired
    private DestinoService destinoService;

    @GetMapping("/viaje/{viajeId}")
    public ResponseEntity<List<Destino>> listarPorViaje(@PathVariable Long viajeId) {
        return ResponseEntity.ok(destinoService.listarPorViaje(viajeId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Destino> obtenerPorId(@PathVariable Long id) {
        return destinoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Destino> crear(@RequestBody Destino destino) {
        Destino nuevoDestino = destinoService.crear(destino);
        return ResponseEntity.status(201).body(nuevoDestino);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Destino> actualizar(@PathVariable Long id, @RequestBody Destino destino) {
        try {
            return ResponseEntity.ok(destinoService.actualizar(id, destino));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        destinoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}