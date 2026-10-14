package com.duoc.rutas_ms.service;

import com.duoc.rutas_ms.model.Parada;
import com.duoc.rutas_ms.model.Ruta;
import com.duoc.rutas_ms.repository.ParadaRepository;
import com.duoc.rutas_ms.repository.RutaRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class ParadaService {

    private final ParadaRepository paradaRepository;
    private final RutaRepository rutaRepository;

    public ParadaService(ParadaRepository paradaRepository, RutaRepository rutaRepository) {
        this.paradaRepository = paradaRepository;
        this.rutaRepository = rutaRepository;
    }

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

    public List<Parada> obtenerTodos() {
        return paradaRepository.findAll();
    }

    public Optional<Parada> buscaPorId(Long id) {
        return paradaRepository.findById(id);
    }

    public Optional<Parada> actualizar(Long id, Parada datos) {
        return paradaRepository.findById(id).map(paradaExistente -> {
            paradaExistente.setDireccion(datos.getDireccion());
            paradaExistente.setOrden(datos.getOrden());
            paradaExistente.setObservacion(datos.getObservacion());
            return paradaRepository.save(paradaExistente);
        });
    }

    public boolean eliminar(Long id) {
        if (paradaRepository.existsById(id)) {
            paradaRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Parada> buscarPorRuta(Long rutaId) {
        return paradaRepository.findByRutaIdOrderByOrdenAsc(rutaId);
    }

    public List<Parada> buscarPorCamion(Long camionId) {
        return paradaRepository.findByRutaCamionIdOrderByOrdenAsc(camionId);
    }

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
