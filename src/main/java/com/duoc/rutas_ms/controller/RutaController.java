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
 * Controlador REST para gestionar las hojas de ruta de recolección.
 */
@RestController
@RequestMapping("/rutas")
public class RutaController {

    private final RutaService rutaService;

    /** Constructor con inyección del servicio de rutas. */
    public RutaController(RutaService rutaService) {
        this.rutaService = rutaService;
    }

    /** Registra una nueva ruta; valida que nombre no sea vacío y camionId no sea nulo. */
    @PostMapping
    public ResponseEntity<Ruta> guardar(@RequestBody Ruta ruta) {
        if (ruta.getNombre() == null || ruta.getNombre().trim().isEmpty() || ruta.getCamionId() == null) {
            return ResponseEntity.badRequest().build();
        }
        Ruta rutaGuardada = rutaService.guardar(ruta);
        return ResponseEntity.status(HttpStatus.CREATED).body(rutaGuardada);
    }

    /** Lista todas las rutas registradas. */
    @GetMapping
    public ResponseEntity<List<Ruta>> obtenerTodos() {
        return ResponseEntity.ok(rutaService.obtenerTodos());
    }

    /** Busca una ruta por su ID. */
    @GetMapping("/{id}")
    public ResponseEntity<Ruta> buscaPorId(@PathVariable Long id) {
        return rutaService.buscaPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /** Actualiza los datos de una ruta existente. */
    @PutMapping("/{id}")
    public ResponseEntity<Ruta> actualizar(@PathVariable Long id, @RequestBody Ruta datos) {
        return rutaService.actualizar(id, datos)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /** Elimina una ruta por su ID. */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (rutaService.eliminar(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    /** Consulta rutas por sector geográfico. */
    @GetMapping("/sector/{sector}")
    public ResponseEntity<List<Ruta>> buscarPorSector(@PathVariable String sector) {
        return ResponseEntity.ok(rutaService.buscarPorSector(sector));
    }

    /** Consulta rutas por estado. */
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Ruta>> buscarPorEstado(@PathVariable String estado) {
        return ResponseEntity.ok(rutaService.buscarPorEstado(estado));
    }

    /** Consulta las hojas de ruta asignadas a un camión específico. */
    @GetMapping("/camion/{camionId}")
    public ResponseEntity<List<Ruta>> buscarPorCamion(@PathVariable Long camionId) {
        return ResponseEntity.ok(rutaService.buscarPorCamion(camionId));
    }
}
