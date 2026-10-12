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
 * Entidad que representa una ruta de recolección en el sistema.
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

    @OneToMany(mappedBy = "ruta", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Parada> paradas = new ArrayList<>();

    /**
     * Constructor vacío requerido por JPA.
     */
    public Ruta() {
    }

    /**
     * Constructor con atributos principales sin id ni lista de paradas.
     *
     * @param nombre Nombre descriptivo de la ruta.
     * @param sector Sector geográfico de la ruta.
     * @param diaRecoleccion Día programado para la recolección.
     * @param estado Estado actual de la ruta (ACTIVA o INACTIVA).
     */
    public Ruta(String nombre, String sector, String diaRecoleccion, String estado) {
        this.nombre = nombre;
        this.sector = sector;
        this.diaRecoleccion = diaRecoleccion;
        this.estado = estado;
    }

    /**
     * Obtiene el identificador único de la ruta.
     *
     * @return Identificador de la ruta.
     */
    public Long getId() {
        return id;
    }

    /**
     * Asigna el identificador único de la ruta.
     *
     * @param id Identificador a asignar.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre de la ruta.
     *
     * @return Nombre de la ruta.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Asigna el nombre de la ruta.
     *
     * @param nombre Nombre a asignar.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el sector asignado a la ruta.
     *
     * @return Sector de la ruta.
     */
    public String getSector() {
        return sector;
    }

    /**
     * Asigna el sector asignado a la ruta.
     *
     * @param sector Sector a asignar.
     */
    public void setSector(String sector) {
        this.sector = sector;
    }

    /**
     * Obtiene el día de recolección programado.
     *
     * @return Día de recolección.
     */
    public String getDiaRecoleccion() {
        return diaRecoleccion;
    }

    /**
     * Asigna el día de recolección programado.
     *
     * @param diaRecoleccion Día de recolección a asignar.
     */
    public void setDiaRecoleccion(String diaRecoleccion) {
        this.diaRecoleccion = diaRecoleccion;
    }

    /**
     * Obtiene el estado actual de la ruta.
     *
     * @return Estado de la ruta.
     */
    public String getEstado() {
        return estado;
    }

    /**
     * Asigna el estado de la ruta.
     *
     * @param estado Estado a asignar (ACTIVA o INACTIVA).
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * Obtiene la lista de paradas asociadas a la ruta.
     *
     * @return Lista de paradas.
     */
    public List<Parada> getParadas() {
        return paradas;
    }

    /**
     * Asigna la lista de paradas asociadas a la ruta.
     *
     * @param paradas Lista de paradas a asignar.
     */
    public void setParadas(List<Parada> paradas) {
        this.paradas = paradas;
    }
}
