package com.biomath3d.model;

/**
 * Contenedor de datos que representa los parámetros de un límite de integración definida.
 * Evita la saturación de argumentos en métodos multivariables.
 */
public class IntegralDefinida {
    private String variableMarcador; // Ejemplo: "x", "y" o "z"
    private double limiteInferior;
    private double limiteSuperior;

    public IntegralDefinida() {}

    public IntegralDefinida(String variableMarcador, double limiteInferior, double limiteSuperior) {
        this.variableMarcador = variableMarcador;
        this.limiteInferior = limiteInferior;
        this.limiteSuperior = limiteSuperior;
    }

    // Getters y Setters
    public String getVariableMarcador() { return variableMarcador; }
    public void setVariableMarcador(String variableMarcador) { this.variableMarcador = variableMarcador; }

    public double getLimiteInferior() { return limiteInferior; }
    public void setLimiteInferior(double limiteInferior) { this.limiteInferior = limiteInferior; }

    public double getLimiteSuperior() { return limiteSuperior; }
    public void setLimiteSuperior(double limiteSuperior) { this.limiteSuperior = limiteSuperior; }
}