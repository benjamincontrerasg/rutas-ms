package com.duoc.rutas_ms.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Entidad que representa una parada o punto de recolección en una ruta.
 */
@Entity
@Table(name = "paradas")
public class Parada {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String direccion;

    private Integer orden;

    private String observacion;

    @ManyToOne
    @JoinColumn(name = "ruta_id")
    private Ruta ruta;

    /**
     * Constructor vacío requerido por JPA.
     */
    public Parada() {
    }

    /**
     * Constructor con atributos principales sin id ni relación de ruta.
     *
     * @param direccion Dirección física del punto de recolección.
     * @param orden Posición o secuencia de la parada en la ruta.
     * @param observacion Información adicional u observaciones de la parada.
     */
    public Parada(String direccion, Integer orden, String observacion) {
        this.direccion = direccion;
        this.orden = orden;
        this.observacion = observacion;
    }

    /**
     * Obtiene el identificador único de la parada.
     *
     * @return Identificador de la parada.
     */
    public Long getId() {
        return id;
    }

    /**
     * Asigna el identificador único de la parada.
     *
     * @param id Identificador a asignar.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Obtiene la dirección física de la parada.
     *
     * @return Dirección de la parada.
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * Asigna la dirección física de la parada.
     *
     * @param direccion Dirección a asignar.
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * Obtiene el número de orden de la parada en la ruta.
     *
     * @return Número de orden.
     */
    public Integer getOrden() {
        return orden;
    }

    /**
     * Asigna el número de orden de la parada en la ruta.
     *
     * @param orden Número de orden a asignar.
     */
    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    /**
     * Obtiene las observaciones de la parada.
     *
     * @return Observación de la parada.
     */
    public String getObservacion() {
        return observacion;
    }

    /**
     * Asigna las observaciones de la parada.
     *
     * @param observacion Observación a asignar.
     */
    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    /**
     * Obtiene la ruta a la cual pertenece esta parada.
     *
     * @return Objeto Ruta asociado.
     */
    public Ruta getRuta() {
        return ruta;
    }

    /**
     * Asigna la ruta a la cual pertenece esta parada.
     *
     * @param ruta Objeto Ruta a asociar.
     */
    public void setRuta(Ruta ruta) {
        this.ruta = ruta;
    }
}
