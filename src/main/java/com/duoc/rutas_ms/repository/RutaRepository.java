package com.duoc.rutas_ms.repository;

import com.duoc.rutas_ms.model.Ruta;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RutaRepository extends JpaRepository<Ruta, Long> {

    List<Ruta> findBySector(String sector);

    List<Ruta> findByEstado(String estado);

    List<Ruta> findByCamionId(Long camionId);
}
