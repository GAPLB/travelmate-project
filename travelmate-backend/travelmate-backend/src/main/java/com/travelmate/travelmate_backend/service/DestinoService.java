package com.travelmate.travelmate_backend.service;

import com.travelmate.travelmate_backend.model.Destino;
import com.travelmate.travelmate_backend.repository.DestinoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class DestinoService {

    @Autowired
    private DestinoRepository destinoRepository;


    public List<Destino> listarPorViaje(Long viajeId) {
        return destinoRepository.findByViajeId(viajeId);
    }


    public Optional<Destino> buscarPorId(Long id) {
        return destinoRepository.findById(id);
    }


    public Destino crear(Destino destino) {
        return destinoRepository.save(destino);
    }


    public Destino actualizar(Long id, Destino datosNuevos) {

        Destino destinoExistente = destinoRepository.findById(id)

                .orElseThrow(() -> new RuntimeException("Destino no encontrado"));

        destinoExistente.setNombre(datosNuevos.getNombre());
        destinoExistente.setLatitud(datosNuevos.getLatitud());
        destinoExistente.setLongitud(datosNuevos.getLongitud());
        destinoExistente.setFechaVisita(datosNuevos.getFechaVisita());

        return destinoRepository.save(destinoExistente);
    }

    public void eliminar(Long id) {
        destinoRepository.deleteById(id);
    }
}

