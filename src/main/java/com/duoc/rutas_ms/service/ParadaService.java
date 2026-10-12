package com.duoc.rutas_ms.service;

import com.duoc.rutas_ms.model.Parada;
import com.duoc.rutas_ms.model.Ruta;
import com.duoc.rutas_ms.repository.ParadaRepository;
import com.duoc.rutas_ms.repository.RutaRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

/**
 * Servicio encargado de gestionar la lógica de negocio para las paradas de una ruta.
 */
@Service
public class ParadaService {

    private final ParadaRepository paradaRepository;
    private final RutaRepository rutaRepository;

    /**
     * Constructor para inyección de dependencias.
     *
     * @param paradaRepository Repositorio de acceso a datos de paradas.
     * @param rutaRepository Repositorio de acceso a datos de rutas para validaciones.
     */
    public ParadaService(ParadaRepository paradaRepository, RutaRepository rutaRepository) {
        this.paradaRepository = paradaRepository;
        this.rutaRepository = rutaRepository;
    }

    /**
     * Guarda una nueva parada asociándola a una ruta existente.
     *
     * @param rutaId Identificador de la ruta a la cual pertenecerá la parada.
     * @param parada Entidad parada con los datos a registrar.
     * @return Optional con la parada guardada, o vacío si la ruta no existe.
     */
    public Optional<Parada> guardar(Long rutaId, Parada parada) {
        Optional<Ruta> rutaOptional = rutaRepository.findById(rutaId);
        if (rutaOptional.isEmpty()) {
            return Optional.empty();
        }
        parada.setRuta(rutaOptional.get());
        return Optional.of(paradaRepository.save(parada));
    }

    /**
     * Obtiene el listado completo de todas las paradas registradas.
     *
     * @return Lista de paradas.
     */
    public List<Parada> obtenerTodos() {
        return paradaRepository.findAll();
    }

    /**
     * Busca una parada específica por su identificador único.
     *
     * @param id Identificador de la parada.
     * @return Optional con la parada encontrada, o vacío si no existe.
     */
    public Optional<Parada> buscaPorId(Long id) {
        return paradaRepository.findById(id);
    }

    /**
     * Actualiza los datos de una parada existente sin modificar su ruta asignada.
     *
     * @param id Identificador de la parada a actualizar.
     * @param datos Objeto con los nuevos datos de la parada (dirección, orden, observación).
     * @return Optional con la parada actualizada, o vacío si no existía.
     */
    public Optional<Parada> actualizar(Long id, Parada datos) {
        return paradaRepository.findById(id).map(paradaExistente -> {
            paradaExistente.setDireccion(datos.getDireccion());
            paradaExistente.setOrden(datos.getOrden());
            paradaExistente.setObservacion(datos.getObservacion());
            return paradaRepository.save(paradaExistente);
        });
    }

    /**
     * Elimina una parada por su ID si existe en el sistema.
     *
     * @param id Identificador de la parada a eliminar.
     * @return true si la parada fue eliminada con éxito, false si no existía.
     */
    public boolean eliminar(Long id) {
        if (paradaRepository.existsById(id)) {
            paradaRepository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * Busca y obtiene las paradas de una ruta específica ordenadas ascendentemente por su orden.
     *
     * @param rutaId Identificador de la ruta.
     * @return Lista de paradas ordenadas de la ruta.
     */
    public List<Parada> buscarPorRuta(Long rutaId) {
        return paradaRepository.findByRutaIdOrderByOrdenAsc(rutaId);
    }
}
