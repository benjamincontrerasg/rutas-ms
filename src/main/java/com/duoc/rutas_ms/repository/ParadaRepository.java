package com.duoc.rutas_ms.repository;

import com.duoc.rutas_ms.model.Parada;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio JPA para la entidad Parada.
 */
@Repository
public interface ParadaRepository extends JpaRepository<Parada, Long> {

    /** Busca paradas asociadas a una ruta ordenadas por posición ascendente. */
    List<Parada> findByRutaIdOrderByOrdenAsc(Long rutaId);

    /** Busca paradas de las rutas asignadas a un camión ordenadas por orden ascendente. */
    List<Parada> findByRutaCamionIdOrderByOrdenAsc(Long camionId);
}
