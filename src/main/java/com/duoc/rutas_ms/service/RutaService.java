package com.duoc.rutas_ms.service;

import com.duoc.rutas_ms.model.Ruta;
import com.duoc.rutas_ms.repository.RutaRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

/**
 * Servicio de negocio para la gestión de hojas de ruta de recolección.
 */
@Service
public class RutaService {

    private final RutaRepository rutaRepository;

    /** Constructor con inyección del repositorio de rutas. */
    public RutaService(RutaRepository rutaRepository) {
        this.rutaRepository = rutaRepository;
    }

    /** Guarda una nueva ruta, asignando estado ACTIVA por defecto si no viene especificado. */
    public Ruta guardar(Ruta ruta) {
        if (ruta.getEstado() == null || ruta.getEstado().trim().isEmpty()) {
            ruta.setEstado("ACTIVA");
        }
        return rutaRepository.save(ruta);
    }

    /** Obtiene todas las rutas registradas. */
    public List<Ruta> obtenerTodos() {
        return rutaRepository.findAll();
    }

    /** Busca una ruta por su ID. */
    public Optional<Ruta> buscaPorId(Long id) {
        return rutaRepository.findById(id);
    }

    /** Actualiza los datos de una ruta existente, incluyendo el camión asignado. */
    public Optional<Ruta> actualizar(Long id, Ruta datos) {
        return rutaRepository.findById(id).map(rutaExistente -> {
            rutaExistente.setNombre(datos.getNombre());
            rutaExistente.setSector(datos.getSector());
            rutaExistente.setDiaRecoleccion(datos.getDiaRecoleccion());
            rutaExistente.setEstado(datos.getEstado());
            rutaExistente.setCamionId(datos.getCamionId());
            return rutaRepository.save(rutaExistente);
        });
    }

    /** Elimina una ruta por su ID si existe. */
    public boolean eliminar(Long id) {
        if (rutaRepository.existsById(id)) {
            rutaRepository.deleteById(id);
            return true;
        }
        return false;
    }

    /** Busca rutas por sector geográfico. */
    public List<Ruta> buscarPorSector(String sector) {
        return rutaRepository.findBySector(sector);
    }

    /** Busca rutas por su estado. */
    public List<Ruta> buscarPorEstado(String estado) {
        return rutaRepository.findByEstado(estado);
    }

    /** Busca las rutas asignadas a un camión específico. */
    public List<Ruta> buscarPorCamion(Long camionId) {
        return rutaRepository.findByCamionId(camionId);
    }
}
