package com.travelmate.travelmate_backend.controller;


import com.travelmate.travelmate_backend.model.ActividadGasto;
import com.travelmate.travelmate_backend.service.ActividadGastoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/actividades-gastos")
public class ActividadGastoController {

    @Autowired

    private ActividadGastoService actividadGastoService;

    @GetMapping("/viaje/{viajeId}")
    public ResponseEntity<List<ActividadGasto>> listarPorViaje(@PathVariable Long viajeId) {
        return ResponseEntity.ok(actividadGastoService.listarPorViaje(viajeId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ActividadGasto> obtenerPorId(@PathVariable Long id) {
        return actividadGastoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ActividadGasto> crear(@RequestBody ActividadGasto actividadGasto) {
        ActividadGasto nuevo = actividadGastoService.crear(actividadGasto);
        return ResponseEntity.status(201).body(nuevo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ActividadGasto> actualizar(@PathVariable Long id, @RequestBody ActividadGasto actividadGasto) {
        try {
            return ResponseEntity.ok(actividadGastoService.actualizar(id, actividadGasto));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        actividadGastoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}