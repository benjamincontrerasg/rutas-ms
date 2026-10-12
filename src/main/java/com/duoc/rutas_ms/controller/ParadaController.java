package com.duoc.rutas_ms.controller;

import com.duoc.rutas_ms.model.Parada;
import com.duoc.rutas_ms.service.ParadaService;
import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador REST que expone los endpoints para la gestión de paradas de una ruta.
 */
@RestController
@RequestMapping("/paradas")
public class ParadaController {

    private final ParadaService paradaService;

    /**
     * Constructor para inyección de dependencias.
     *
     * @param paradaService Servicio con la lógica de negocio de paradas.
     */
    public ParadaController(ParadaService paradaService) {
        this.paradaService = paradaService;
    }

    /**
     * Registra una nueva parada asociada a una ruta existente.
     * Valida primero que la dirección no esté vacía y luego la existencia de la ruta.
     *
     * @param rutaId Identificador de la ruta a la cual asociar la parada.
     * @param parada Objeto Parada a registrar.
     * @return 201 Created con la parada guardada, 400 Bad Request si la dirección está vacía,
     *         o 404 Not Found si la ruta no existe.
     */
    @PostMapping("/ruta/{rutaId}")
    public ResponseEntity<Parada> guardar(@PathVariable Long rutaId, @RequestBody Parada parada) {
        if (parada.getDireccion() == null || parada.getDireccion().trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        Optional<Parada> paradaGuardada = paradaService.guardar(rutaId, parada);
        return paradaGuardada
                .map(guardada -> ResponseEntity.status(HttpStatus.CREATED).body(guardada))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Obtiene el listado completo de todas las paradas registradas.
     *
     * @return 200 OK con la lista de paradas.
     */
    @GetMapping
    public ResponseEntity<List<Parada>> obtenerTodos() {
        return ResponseEntity.ok(paradaService.obtenerTodos());
    }

    /**
     * Obtiene una parada específica por su identificador.
     *
     * @param id Identificador de la parada.
     * @return 200 OK con la parada si existe, o 404 Not Found si no existe.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Parada> buscaPorId(@PathVariable Long id) {
        return paradaService.buscaPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Actualiza los datos de una parada existente (dirección, orden, observación).
     *
     * @param id Identificador de la parada a actualizar.
     * @param datos Objeto con los nuevos datos.
     * @return 200 OK con la parada actualizada, o 404 Not Found si no existe.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Parada> actualizar(@PathVariable Long id, @RequestBody Parada datos) {
        return paradaService.actualizar(id, datos)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Elimina una parada por su identificador.
     *
     * @param id Identificador de la parada a eliminar.
     * @return 204 No Content si se eliminó, o 404 Not Found si no existía.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (paradaService.eliminar(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Obtiene todas las paradas pertenecientes a una ruta específica ordenadas por su posición.
     *
     * @param rutaId Identificador de la ruta.
     * @return 200 OK con la lista ordenada de paradas asociadas a la ruta.
     */
    @GetMapping("/ruta/{rutaId}")
    public ResponseEntity<List<Parada>> buscarPorRuta(@PathVariable Long rutaId) {
        return ResponseEntity.ok(paradaService.buscarPorRuta(rutaId));
    }
}
