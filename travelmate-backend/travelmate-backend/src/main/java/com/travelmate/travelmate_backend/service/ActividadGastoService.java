package com.travelmate.travelmate_backend.service;


import com.travelmate.travelmate_backend.model.ActividadGasto;
import com.travelmate.travelmate_backend.repository.ActividadGastoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ActividadGastoService {

    @Autowired

    private ActividadGastoRepository actividadGastoRepository;


    public List<ActividadGasto> listarPorViaje(Long viajeId) {
        return actividadGastoRepository.findByViajeId(viajeId);
    }


    public Optional<ActividadGasto> buscarPorId(Long id) {
        return actividadGastoRepository.findById(id);
    }


    public ActividadGasto crear(ActividadGasto actividadGasto) {
        return actividadGastoRepository.save(actividadGasto);
    }


    public ActividadGasto actualizar(Long id, ActividadGasto datosNuevos) {
        ActividadGasto existente = actividadGastoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Actividad/Gasto no encontrado"));

        existente.setConcepto(datosNuevos.getConcepto());
        existente.setMonto(datosNuevos.getMonto());
        existente.setCategoria(datosNuevos.getCategoria());
        existente.setFecha(datosNuevos.getFecha());

        return actividadGastoRepository.save(existente);
    }

    public void eliminar(Long id) {
        actividadGastoRepository.deleteById(id);
    }
}