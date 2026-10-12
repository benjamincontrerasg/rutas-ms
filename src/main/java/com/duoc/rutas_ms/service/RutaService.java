package com.duoc.rutas_ms.service;

import com.duoc.rutas_ms.model.Ruta;
import com.duoc.rutas_ms.repository.RutaRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class RutaService {

    private final RutaRepository rutaRepository;

    public RutaService(RutaRepository rutaRepository) {
        this.rutaRepository = rutaRepository;
    }

    public Ruta guardar(Ruta ruta) {
        if (ruta.getEstado() == null || ruta.getEstado().trim().isEmpty()) {
            ruta.setEstado("ACTIVA");
        }
        return rutaRepository.save(ruta);
    }

    public List<Ruta> obtenerTodos() {
        return rutaRepository.findAll();
    }

    public Optional<Ruta> buscaPorId(Long id) {
        return rutaRepository.findById(id);
    }

    public Optional<Ruta> actualizar(Long id, Ruta datos) {
        return rutaRepository.findById(id).map(rutaExistente -> {
            rutaExistente.setNombre(datos.getNombre());
            rutaExistente.setSector(datos.getSector());
            rutaExistente.setDiaRecoleccion(datos.getDiaRecoleccion());
            rutaExistente.setEstado(datos.getEstado());
            return rutaRepository.save(rutaExistente);
        });
    }

    public boolean eliminar(Long id) {
        if (rutaRepository.existsById(id)) {
            rutaRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Ruta> buscarPorSector(String sector) {
        return rutaRepository.findBySector(sector);
    }

    public List<Ruta> buscarPorEstado(String estado) {
        return rutaRepository.findByEstado(estado);
    }
}
