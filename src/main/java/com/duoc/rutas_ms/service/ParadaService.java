package com.duoc.rutas_ms.service;

import com.duoc.rutas_ms.model.Parada;
import com.duoc.rutas_ms.model.Ruta;
import com.duoc.rutas_ms.repository.ParadaRepository;
import com.duoc.rutas_ms.repository.RutaRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

/**
 * Servicio de negocio para la gestión de paradas y registro de resultados de retiro.
 */
@Service
public class ParadaService {

    private final ParadaRepository paradaRepository;
    private final RutaRepository rutaRepository;

    /** Constructor con inyección de repositorios. */
    public ParadaService(ParadaRepository paradaRepository, RutaRepository rutaRepository) {
        this.paradaRepository = paradaRepository;
        this.rutaRepository = rutaRepository;
    }

    /** Asocia una parada a una ruta existente y establece estado PENDIENTE por defecto. */
    public Optional<Parada> guardar(Long rutaId, Parada parada) {
        Optional<Ruta> rutaOptional = rutaRepository.findById(rutaId);
        if (rutaOptional.isEmpty()) {
            return Optional.empty();
        }
        parada.setRuta(rutaOptional.get());
        if (parada.getEstado() == null || parada.getEstado().trim().isEmpty()) {
            parada.setEstado("PENDIENTE");
        }
        return Optional.of(paradaRepository.save(parada));
    }

    /** Obtiene todas las paradas registradas. */
    public List<Parada> obtenerTodos() {
        return paradaRepository.findAll();
    }

    /** Busca una parada por su ID. */
    public Optional<Parada> buscaPorId(Long id) {
        return paradaRepository.findById(id);
    }

    /** Actualiza los datos informativos de una parada sin modificar su estado. */
    public Optional<Parada> actualizar(Long id, Parada datos) {
        return paradaRepository.findById(id).map(paradaExistente -> {
            paradaExistente.setDireccion(datos.getDireccion());
            paradaExistente.setOrden(datos.getOrden());
            paradaExistente.setObservacion(datos.getObservacion());
            return paradaRepository.save(paradaExistente);
        });
    }

    /** Elimina una parada por su ID si existe. */
    public boolean eliminar(Long id) {
        if (paradaRepository.existsById(id)) {
            paradaRepository.deleteById(id);
            return true;
        }
        return false;
    }

    /** Busca las paradas asignadas a una ruta ordenadas por orden ascendente. */
    public List<Parada> buscarPorRuta(Long rutaId) {
        return paradaRepository.findByRutaIdOrderByOrdenAsc(rutaId);
    }

    /** Busca las paradas de las rutas asignadas a un camión ordenadas por orden ascendente. */
    public List<Parada> buscarPorCamion(Long camionId) {
        return paradaRepository.findByRutaCamionIdOrderByOrdenAsc(camionId);
    }

    /** Registra el resultado del retiro (REALIZADA o FALLIDA) y observación si se proporciona. */
    public Optional<Parada> registrarResultado(Long id, String estado, String observacion) {
        return paradaRepository.findById(id).map(paradaExistente -> {
            paradaExistente.setEstado(estado);
            if (observacion != null) {
                paradaExistente.setObservacion(observacion);
            }
            return paradaRepository.save(paradaExistente);
        });
    }
}
