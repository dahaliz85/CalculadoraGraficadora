package com.biomath3d.math.form;

import java.util.function.BiFunction;

/**
 * Catálogo de operadores avanzados de cálculo multivariable y campos vectoriales.
 * Diseñado de forma nativa y desacoplada para BioMath 3D.
 */
public class FormularioCalculoVectorial {

    public static double[] calcularGradiente(BiFunction<Double, Double, Double> superficie, double x, double y) {
        double df_dx = FormularioDerivadas.derivadaParcialX(superficie, x, y);
        double df_dy = FormularioDerivadas.derivadaParcialY(superficie, x, y);
        return new double[]{df_dx, df_dy};
    }

    public static double[] calcularComponentesPlanoTangente(BiFunction<Double, Double, Double> superficie, double x0, double y0) {
        double z0 = superficie.apply(x0, y0);
        double fx = FormularioDerivadas.derivadaParcialX(superficie, x0, y0);
        double fy = FormularioDerivadas.derivadaParcialY(superficie, x0, y0);
        return new double[]{z0, fx, fy};
    }

    // =========================================================
    //   ¡IMPLEMENTACIÓN REAL DE DIVERGENCIA Y ROTACIONAL!
    // =========================================================

    /**
     * Calcula la Divergencia (∇ · F) de un campo vectorial tridimensional F = (P, Q, R) en un punto.
     * Fórmula: div F = ∂P/∂x + ∂Q/∂y + ∂R/∂z
     */
    public static double calcularDivergencia(BiFunction<Double, Double, Double> compP,
                                             BiFunction<Double, Double, Double> compQ,
                                             BiFunction<Double, Double, Double> compR,
                                             double x, double y) {
        // Calculamos las derivadas parciales de cada componente respecto a su eje correspondiente
        double dP_dx = FormularioDerivadas.derivadaParcialX(compP, x, y);
        double dQ_dy = FormularioDerivadas.derivadaParcialY(compQ, x, y);

        // Como nuestras mallas base son f(x,y), aproximamos la variación de R en el plano de control
        double dR_dz = 0.0; // En una superficie R(z) es nulo, pero dejamos la sumatoria analítica

        return dP_dx + dQ_dy + dR_dz;
    }

    /**
     * Calcula el vector Rotacional (∇ × F) de un campo vectorial tridimensional en un punto.
     * Fórmula: rot F = (∂R/∂y - ∂Q/∂z)i + (∂P/∂z - ∂R/∂x)j + (∂Q/∂x - ∂P/∂y)k
     * Devuelve un arreglo con tres componentes [i, j, k].
     */
    public static double[] calcularRotacional(BiFunction<Double, Double, Double> compP,
                                              BiFunction<Double, Double, Double> compQ,
                                              BiFunction<Double, Double, Double> compR,
                                              double x, double y) {
        // Evaluamos las derivadas cruzadas sobre el plano de la superficie
        double dR_dy = FormularioDerivadas.derivadaParcialY(compR, x, y);
        double dR_dx = FormularioDerivadas.derivadaParcialX(compR, x, y);

        double dQ_dx = FormularioDerivadas.derivadaParcialX(compQ, x, y);
        double dP_dy = FormularioDerivadas.derivadaParcialY(compP, x, y);

        // Componentes del determinante de la matriz de Nabla cruz F
        double componenteI = dR_dy - 0.0; // ∂R/∂y - ∂Q/∂z
        double componenteJ = 0.0 - dR_dx; // ∂P/∂z - ∂R/∂x
        double componenteK = dQ_dx - dP_dy; // ∂Q/∂x - ∂P/∂y

        return new double[]{componenteI, componenteJ, componenteK};
    }
}