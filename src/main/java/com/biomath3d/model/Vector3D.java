package com.biomath3d.model;

/**
 * Modelo matemático que representa un vector en el espacio tridimensional (i, j, k).
 */
public class Vector3D {
    private double componentI;
    private double componentJ;
    private double componentK;

    public Vector3D() {}

    public Vector3D(double componentI, double componentJ, double componentK) {
        this.componentI = componentI;
        this.componentJ = componentJ;
        this.componentK = componentK;
    }

    // Getters y Setters
    public double getComponentI() { return componentI; }
    public void setComponentI(double componentI) { this.componentI = componentI; }

    public double getComponentJ() { return componentJ; }
    public void setComponentJ(double componentJ) { this.componentJ = componentJ; }

    public double getComponentK() { return componentK; }
    public void setComponentK(double componentK) { this.componentK = componentK; }

    @Override
    public String toString() {
        return String.format("%.3fi + %.3fj + %.3fk", componentI, componentJ, componentK);
    }
}