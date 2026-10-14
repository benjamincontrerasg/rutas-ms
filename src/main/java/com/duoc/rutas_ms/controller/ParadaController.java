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

@RestController
@RequestMapping("/paradas")
public class ParadaController {

    private final ParadaService paradaService;

    public ParadaController(ParadaService paradaService) {
        this.paradaService = paradaService;
    }

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

    @GetMapping
    public ResponseEntity<List<Parada>> obtenerTodos() {
        return ResponseEntity.ok(paradaService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Parada> buscaPorId(@PathVariable Long id) {
        return paradaService.buscaPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Parada> actualizar(@PathVariable Long id, @RequestBody Parada datos) {
        return paradaService.actualizar(id, datos)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (paradaService.eliminar(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/ruta/{rutaId}")
    public ResponseEntity<List<Parada>> buscarPorRuta(@PathVariable Long rutaId) {
        return ResponseEntity.ok(paradaService.buscarPorRuta(rutaId));
    }

    @GetMapping("/camion/{camionId}")
    public ResponseEntity<List<Parada>> buscarPorCamion(@PathVariable Long camionId) {
        return ResponseEntity.ok(paradaService.buscarPorCamion(camionId));
    }

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
