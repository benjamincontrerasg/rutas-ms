package com.duoc.rutas_ms.repository;

import com.duoc.rutas_ms.model.Parada;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParadaRepository extends JpaRepository<Parada, Long> {

    List<Parada> findByRutaIdOrderByOrdenAsc(Long rutaId);
}
