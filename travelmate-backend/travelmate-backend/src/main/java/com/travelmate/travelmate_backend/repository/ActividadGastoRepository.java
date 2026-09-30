package com.travelmate.travelmate_backend.repository;


import com.travelmate.travelmate_backend.model.ActividadGasto;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ActividadGastoRepository extends JpaRepository<ActividadGasto, Long> {

    List<ActividadGasto> findByViajeId(Long viajeId);
}