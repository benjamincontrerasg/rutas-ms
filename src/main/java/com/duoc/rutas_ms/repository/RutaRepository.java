package com.duoc.rutas_ms.repository;

import com.duoc.rutas_ms.model.Ruta;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio JPA para la entidad Ruta.
 * Proporciona operaciones de persistencia y consultas personalizadas sobre las rutas.
 */
@Repository
public interface RutaRepository extends JpaRepository<Ruta, Long> {

    /**
     * Busca y obtiene todas las rutas asociadas a un sector específico.
     *
     * @param sector Sector geográfico por el cual filtrar.
     * @return Lista de rutas encontradas en el sector.
     */
    List<Ruta> findBySector(String sector);

    /**
     * Busca y obtiene todas las rutas asociadas a un estado específico.
     *
     * @param estado Estado por el cual filtrar (ACTIVA o INACTIVA).
     * @return Lista de rutas con el estado indicado.
     */
    List<Ruta> findByEstado(String estado);
}
