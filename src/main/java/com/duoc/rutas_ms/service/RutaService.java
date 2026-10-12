package com.duoc.rutas_ms.service;

import com.duoc.rutas_ms.model.Ruta;
import com.duoc.rutas_ms.repository.RutaRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

/**
 * Servicio encargado de gestionar la lógica de negocio para las rutas de recolección.
 */
@Service
public class RutaService {

    private final RutaRepository rutaRepository;

    /**
     * Constructor para inyección de dependencias.
     *
     * @param rutaRepository Repositorio de acceso a datos de rutas.
     */
    public RutaService(RutaRepository rutaRepository) {
        this.rutaRepository = rutaRepository;
    }

    /**
     * Guarda una nueva ruta. Si el estado no está definido, se asigna 'ACTIVA' por defecto.
     *
     * @param ruta Entidad de la ruta a guardar.
     * @return Ruta guardada en la base de datos.
     */
    public Ruta guardar(Ruta ruta) {
        if (ruta.getEstado() == null || ruta.getEstado().trim().isEmpty()) {
            ruta.setEstado("ACTIVA");
        }
        return rutaRepository.save(ruta);
    }

    /**
     * Obtiene el listado completo de rutas registradas.
     *
     * @return Lista de todas las rutas.
     */
    public List<Ruta> obtenerTodos() {
        return rutaRepository.findAll();
    }

    /**
     * Busca una ruta específica por su identificador único.
     *
     * @param id Identificador de la ruta.
     * @return Optional con la ruta si fue encontrada, o vacío en caso contrario.
     */
    public Optional<Ruta> buscaPorId(Long id) {
        return rutaRepository.findById(id);
    }

    /**
     * Actualiza los datos de una ruta existente identificada por su ID.
     *
     * @param id Identificador de la ruta a actualizar.
     * @param datos Objeto con los nuevos datos de la ruta.
     * @return Optional con la ruta actualizada, o vacío si no existía.
     */
    public Optional<Ruta> actualizar(Long id, Ruta datos) {
        return rutaRepository.findById(id).map(rutaExistente -> {
            rutaExistente.setNombre(datos.getNombre());
            rutaExistente.setSector(datos.getSector());
            rutaExistente.setDiaRecoleccion(datos.getDiaRecoleccion());
            rutaExistente.setEstado(datos.getEstado());
            return rutaRepository.save(rutaExistente);
        });
    }

    /**
     * Elimina una ruta por su ID si existe en el sistema.
     *
     * @param id Identificador de la ruta a eliminar.
     * @return true si la ruta fue eliminada con éxito, false si no existía.
     */
    public boolean eliminar(Long id) {
        if (rutaRepository.existsById(id)) {
            rutaRepository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * Busca rutas filtradas por su sector geográfico.
     *
     * @param sector Sector geográfico a consultar.
     * @return Lista de rutas pertenecientes al sector.
     */
    public List<Ruta> buscarPorSector(String sector) {
        return rutaRepository.findBySector(sector);
    }

    /**
     * Busca rutas filtradas por su estado actual.
     *
     * @param estado Estado a consultar (ACTIVA o INACTIVA).
     * @return Lista de rutas con el estado indicado.
     */
    public List<Ruta> buscarPorEstado(String estado) {
        return rutaRepository.findByEstado(estado);
    }
}
