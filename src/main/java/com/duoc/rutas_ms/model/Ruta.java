package com.duoc.rutas_ms.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidad que representa una hoja de ruta de recolección para un camión en RutaLimpia.
 */
@Entity
@Table(name = "rutas")
public class Ruta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private String sector;

    private String diaRecoleccion;

    private String estado;

    private Long camionId;

    @OneToMany(mappedBy = "ruta", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Parada> paradas = new ArrayList<>();

    /** Constructor vacío requerido por JPA. */
    public Ruta() {
    }

    /** Constructor con campos para la creación de una ruta asociada a un camión. */
    public Ruta(String nombre, String sector, String diaRecoleccion, Long camionId) {
        this.nombre = nombre;
        this.sector = sector;
        this.diaRecoleccion = diaRecoleccion;
        this.camionId = camionId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getDiaRecoleccion() {
        return diaRecoleccion;
    }

    public void setDiaRecoleccion(String diaRecoleccion) {
        this.diaRecoleccion = diaRecoleccion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Long getCamionId() {
        return camionId;
    }

    public void setCamionId(Long camionId) {
        this.camionId = camionId;
    }

    public List<Parada> getParadas() {
        return paradas;
    }

    public void setParadas(List<Parada> paradas) {
        this.paradas = paradas;
    }
}
