package com.biomath3d.entity;

/**
 * Entidad que simula la tabla 'simulaciones' en una base de datos relacional.
 * Muestra el mapeo lógico de la persistencia de datos.
 */
public class SimulacionEntity {

    // @Id - Primary Key Auto-Increment
    private Integer idSimulacion;

    // @Column(length = 100)
    private String nombre;

    // @Column(nullable = false, length = 255)
    private String ecuacionFormulada;

    public SimulacionEntity() {}

    public SimulacionEntity(Integer idSimulacion, String nombre, String ecuacionFormulada) {
        this.idSimulacion = idSimulacion;
        this.nombre = nombre;
        this.ecuacionFormulada = ecuacionFormulada;
    }

    // Getters y Setters
    public Integer getIdSimulacion() { return idSimulacion; }
    public void setIdSimulacion(Integer idSimulacion) { this.idSimulacion = idSimulacion; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEcuacionFormulada() { return ecuacionFormulada; }
    public void setEcuacionFormulada(String ecuacionFormulada) { this.ecuacionFormulada = ecuacionFormulada; }
}