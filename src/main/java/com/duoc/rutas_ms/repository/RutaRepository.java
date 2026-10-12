package com.duoc.rutas_ms.repository;

import com.duoc.rutas_ms.model.Ruta;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio JPA para la entidad Ruta.
 */
@Repository
public interface RutaRepository extends JpaRepository<Ruta, Long> {

    /** Busca rutas por su sector geográfico. */
    List<Ruta> findBySector(String sector);

    /** Busca rutas por su estado. */
    List<Ruta> findByEstado(String estado);

    /** Busca rutas asignadas a un camión específico. */
    List<Ruta> findByCamionId(Long camionId);
}
