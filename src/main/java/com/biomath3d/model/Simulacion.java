package com.biomath3d.model;

/**
 * Modelo de negocio que representa una simulación matemática en BioMath 3D.
 * Se utiliza para transportar los datos a través de las capas de la aplicación.
 */
public class Simulacion {
    private int id;
    private String nombre;
    private String ecuacion;
    private double xMin;
    private double xMax;
    private double yMin;
    private double yMax;
    private double constanteA;

    public Simulacion() {}

    public Simulacion(int id, String nombre, String ecuacion, double xMin, double xMax, double yMin, double yMax, double constanteA) {
        this.id = id;
        this.nombre = nombre;
        this.ecuacion = ecuacion;
        this.xMin = xMin;
        this.xMax = xMax;
        this.yMin = yMin;
        this.yMax = yMax;
        this.constanteA = constanteA;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEcuacion() { return ecuacion; }
    public void setEcuacion(String ecuacion) { this.ecuacion = ecuacion; }

    public double getXMin() { return xMin; }
    public void setXMin(double xMin) { this.xMin = xMin; }

    public double getXMax() { return xMax; }
    public void setXMax(double xMax) { this.xMax = xMax; }

    public double getYMin() { return yMin; }
    public void setYMin(double yMin) { this.yMin = yMin; }

    public double getYMax() { return yMax; }
    public void setYMax(double yMax) { this.yMax = yMax; }

    public double getConstanteA() { return constanteA; }
    public void setConstanteA(double constanteA) { this.constanteA = constanteA; }
}