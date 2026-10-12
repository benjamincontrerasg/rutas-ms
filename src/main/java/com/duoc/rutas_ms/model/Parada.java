package com.duoc.rutas_ms.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Entidad que representa una parada de retiro asignada dentro de una ruta.
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

    private String estado;

    private Long solicitudId;

    @ManyToOne
    @JoinColumn(name = "ruta_id")
    private Ruta ruta;

    /** Constructor vacío requerido por JPA. */
    public Parada() {
    }

    /** Constructor con campos para el registro de una parada asociada a una solicitud. */
    public Parada(String direccion, Integer orden, String observacion, Long solicitudId) {
        this.direccion = direccion;
        this.orden = orden;
        this.observacion = observacion;
        this.solicitudId = solicitudId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Long getSolicitudId() {
        return solicitudId;
    }

    public void setSolicitudId(Long solicitudId) {
        this.solicitudId = solicitudId;
    }

    public Ruta getRuta() {
        return ruta;
    }

    public void setRuta(Ruta ruta) {
        this.ruta = ruta;
    }
}
