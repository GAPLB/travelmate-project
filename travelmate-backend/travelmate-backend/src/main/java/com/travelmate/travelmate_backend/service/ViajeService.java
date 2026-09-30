package com.travelmate.travelmate_backend.service;

//¿Por qué no llamar directo al repository desde el controller?
// Porque si mañana necesitas agregar una regla (ej. "no se puede crear un
// viaje con fecha_fin anterior a fecha_inicio"), la pones aquí, en un solo
// lugar — en vez de repetirla en cada endpoint que toque viajes.

import com.travelmate.travelmate_backend.model.Viaje;
import com.travelmate.travelmate_backend.repository.ViajeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service   // le dice a Spring: "esta clase contiene lógica de negocio, gestiónala como componente"

public class ViajeService {

    @Autowired
    private ViajeRepository viajeRepository;


    // Listar todos los viajes de un usuario específico
    public List<Viaje> listarPorUsuario(Long usuarioId) {
        return viajeRepository.findByUsuarioId(usuarioId);
    }


    // Buscar un viaje por su id (puede no existir, por eso Optional)
    public Optional<Viaje> buscarPorId(Long id) {
        return viajeRepository.findById(id);
    }


    // Crear un nuevo viaje
    public Viaje crear(Viaje viaje) {
        return viajeRepository.save(viaje);
    }


    // Actualizar un viaje existente
    public Viaje actualizar(Long id, Viaje datosNuevos) {
        Viaje viajeExistente = viajeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Viaje no encontrado"));


        // Copiamos los campos nuevos sobre el viaje que ya existe en la BD
        viajeExistente.setTitulo(datosNuevos.getTitulo());
        viajeExistente.setDescripcion(datosNuevos.getDescripcion());
        viajeExistente.setFechaInicio(datosNuevos.getFechaInicio());
        viajeExistente.setFechaFin(datosNuevos.getFechaFin());


        return viajeRepository.save(viajeExistente);
    }


    // Eliminar un viaje por id
    public void eliminar(Long id) {
        viajeRepository.deleteById(id);
    }
}