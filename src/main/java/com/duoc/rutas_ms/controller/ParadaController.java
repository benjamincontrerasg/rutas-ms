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
 * Controlador REST para gestionar las paradas de una ruta y registrar resultados de retiros.
 */
@RestController
@RequestMapping("/paradas")
public class ParadaController {

    private final ParadaService paradaService;

    /** Constructor con inyección del servicio de paradas. */
    public ParadaController(ParadaService paradaService) {
        this.paradaService = paradaService;
    }

    /** Crea una parada asociada a una ruta; valida dirección y solicitudId. */
    @PostMapping("/ruta/{rutaId}")
    public ResponseEntity<Parada> guardar(@PathVariable Long rutaId, @RequestBody Parada parada) {
        if (parada.getDireccion() == null || parada.getDireccion().trim().isEmpty() || parada.getSolicitudId() == null) {
            return ResponseEntity.badRequest().build();
        }
        Optional<Parada> paradaGuardada = paradaService.guardar(rutaId, parada);
        return paradaGuardada
                .map(guardada -> ResponseEntity.status(HttpStatus.CREATED).body(guardada))
                .orElse(ResponseEntity.notFound().build());
    }

    /** Lista todas las paradas registradas. */
    @GetMapping
    public ResponseEntity<List<Parada>> obtenerTodos() {
        return ResponseEntity.ok(paradaService.obtenerTodos());
    }

    /** Busca una parada por su ID. */
    @GetMapping("/{id}")
    public ResponseEntity<Parada> buscaPorId(@PathVariable Long id) {
        return paradaService.buscaPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /** Actualiza los datos informativos de una parada existente. */
    @PutMapping("/{id}")
    public ResponseEntity<Parada> actualizar(@PathVariable Long id, @RequestBody Parada datos) {
        return paradaService.actualizar(id, datos)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /** Elimina una parada por su ID. */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (paradaService.eliminar(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    /** Obtiene las paradas asociadas a una ruta ordenadas por posición. */
    @GetMapping("/ruta/{rutaId}")
    public ResponseEntity<List<Parada>> buscarPorRuta(@PathVariable Long rutaId) {
        return ResponseEntity.ok(paradaService.buscarPorRuta(rutaId));
    }

    /** Obtiene las paradas asignadas a un camión ordenadas por orden ascendente. */
    @GetMapping("/camion/{camionId}")
    public ResponseEntity<List<Parada>> buscarPorCamion(@PathVariable Long camionId) {
        return ResponseEntity.ok(paradaService.buscarPorCamion(camionId));
    }

    /** Registra el resultado del retiro realizado por el conductor (REALIZADA o FALLIDA). */
    @PutMapping("/{id}/resultado")
    public ResponseEntity<Parada> registrarResultado(@PathVariable Long id, @RequestBody Parada datos) {
        String estado = datos.getEstado();
        if (!"REALIZADA".equals(estado) && !"FALLIDA".equals(estado)) {
            return ResponseEntity.badRequest().build();
        }
        return paradaService.registrarResultado(id, estado, datos.getObservacion())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
