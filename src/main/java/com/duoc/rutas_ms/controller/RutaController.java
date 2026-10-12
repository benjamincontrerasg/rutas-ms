package com.duoc.rutas_ms.controller;

import com.duoc.rutas_ms.model.Ruta;
import com.duoc.rutas_ms.service.RutaService;
import java.util.List;
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
 * Controlador REST que expone los endpoints para la gestión de rutas de recolección.
 */
@RestController
@RequestMapping("/rutas")
public class RutaController {

    private final RutaService rutaService;

    /**
     * Constructor para inyección de dependencias.
     *
     * @param rutaService Servicio con la lógica de negocio de rutas.
     */
    public RutaController(RutaService rutaService) {
        this.rutaService = rutaService;
    }

    /**
     * Crea y registra una nueva ruta en el sistema.
     *
     * @param ruta Datos de la ruta a registrar.
     * @return 201 Created con la ruta guardada, o 400 Bad Request si el nombre es nulo o vacío.
     */
    @PostMapping
    public ResponseEntity<Ruta> guardar(@RequestBody Ruta ruta) {
        if (ruta.getNombre() == null || ruta.getNombre().trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        Ruta rutaGuardada = rutaService.guardar(ruta);
        return ResponseEntity.status(HttpStatus.CREATED).body(rutaGuardada);
    }

    /**
     * Obtiene el listado de todas las rutas de recolección.
     *
     * @return 200 OK con la lista de rutas.
     */
    @GetMapping
    public ResponseEntity<List<Ruta>> obtenerTodos() {
        return ResponseEntity.ok(rutaService.obtenerTodos());
    }

    /**
     * Obtiene una ruta por su identificador único.
     *
     * @param id Identificador de la ruta.
     * @return 200 OK con la ruta si existe, o 404 Not Found si no existe.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Ruta> buscaPorId(@PathVariable Long id) {
        return rutaService.buscaPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Actualiza los datos de una ruta existente.
     *
     * @param id Identificador de la ruta a actualizar.
     * @param datos Objeto con los datos actualizados.
     * @return 200 OK con la ruta actualizada, o 404 Not Found si no existe.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Ruta> actualizar(@PathVariable Long id, @RequestBody Ruta datos) {
        return rutaService.actualizar(id, datos)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Elimina una ruta por su identificador.
     *
     * @param id Identificador de la ruta a eliminar.
     * @return 204 No Content si se eliminó, o 404 Not Found si no existía.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (rutaService.eliminar(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Busca y obtiene rutas asociadas a un sector específico.
     *
     * @param sector Nombre del sector geográfico.
     * @return 200 OK con la lista de rutas pertenecientes al sector.
     */
    @GetMapping("/sector/{sector}")
    public ResponseEntity<List<Ruta>> buscarPorSector(@PathVariable String sector) {
        return ResponseEntity.ok(rutaService.buscarPorSector(sector));
    }

    /**
     * Busca y obtiene rutas asociadas a un estado específico.
     *
     * @param estado Estado de la ruta (ACTIVA o INACTIVA).
     * @return 200 OK con la lista de rutas con dicho estado.
     */
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Ruta>> buscarPorEstado(@PathVariable String estado) {
        return ResponseEntity.ok(rutaService.buscarPorEstado(estado));
    }
}
