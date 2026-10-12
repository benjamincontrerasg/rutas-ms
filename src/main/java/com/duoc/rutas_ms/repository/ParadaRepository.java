package com.duoc.rutas_ms.repository;

import com.duoc.rutas_ms.model.Parada;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio JPA para la entidad Parada.
 * Proporciona operaciones de persistencia y consultas personalizadas sobre las paradas.
 */
@Repository
public interface ParadaRepository extends JpaRepository<Parada, Long> {

    /**
     * Busca y obtiene las paradas asociadas a una ruta específica, ordenadas ascendentemente por orden.
     *
     * @param rutaId Identificador de la ruta asociada.
     * @return Lista de paradas pertenecientes a la ruta ordenadas por su posición.
     */
    List<Parada> findByRutaIdOrderByOrdenAsc(Long rutaId);
}
