package com.travelmate.travelmate_backend.repository;


import com.travelmate.travelmate_backend.model.Destino;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface DestinoRepository extends JpaRepository<Destino, Long> {


    // Lista solo los destinos DE UN viaje específico (igual que hicimos con findByUsuarioId)
    List<Destino> findByViajeId(Long viajeId);
}