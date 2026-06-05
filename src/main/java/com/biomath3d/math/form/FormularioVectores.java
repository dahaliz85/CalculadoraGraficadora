package com.biomath3d.math.form;

/**
 * Catálogo de operaciones analíticas y numéricas con vectores en R2 y R3.
 * Diseñado de forma nativa para BioMath 3D.
 */
public class FormularioVectores {

    /** Suma de dos vectores: u + v */
    public static double[] sumarVectores(double[] u, double[] v) {
        if (u.length != v.length) {
            throw new IllegalArgumentException("Los vectores deben tener la misma dimensión.");
        }
        double[] resultado = new double[u.length];
        for (int i = 0; i < u.length; i++) {
            resultado[i] = u[i] + v[i];
        }
        return resultado;
    }

    /** Resta de dos vectores: u - v */
    public static double[] restarVectores(double[] u, double[] v) {
        if (u.length != v.length) {
            throw new IllegalArgumentException("Los vectores deben tener la misma dimensión.");
        }
        double[] resultado = new double[u.length];
        for (int i = 0; i < u.length; i++) {
            resultado[i] = u[i] - v[i];
        }
        return resultado;
    }

    /** Multiplicación de un vector por un escalar: c * u */
    public static double[] multiplicarPorEscalar(double[] u, double c) {
        double[] resultado = new double[u.length];
        for (int i = 0; i < u.length; i++) {
            resultado[i] = u[i] * c;
        }
        return resultado;
    }

    /** Magnitud o módulo de un vector: ||u|| = √(x² + y² + z²) */
    public static double calcularMagnitud(double[] u) {
        double sumaCuadrados = 0.0;
        for (double componente : u) {
            sumaCuadrados += componente * componente;
        }
        return Math.sqrt(sumaCuadrados);
    }

    /** Vector unitario: u / ||u|| */
    public static double[] obtenerVectorUnitario(double[] u) {
        double magnitud = calcularMagnitud(u);
        if (magnitud == 0.0) {
            throw new ArithmeticException("No se puede normalizar un vector nulo (magnitud cero).");
        }
        double[] resultado = new double[u.length];
        for (int i = 0; i < u.length; i++) {
            resultado[i] = u[i] / magnitud;
        }
        return resultado;
    }

    /** Producto punto (Producto escalar): u · v = u1*v1 + u2*v2 + u3*v3 */
    public static double calcularProductoPunto(double[] u, double[] v) {
        if (u.length != v.length) {
            throw new IllegalArgumentException("Los vectores deben tener la misma dimensión.");
        }
        double productoPunto = 0.0;
        for (int i = 0; i < u.length; i++) {
            productoPunto += u[i] * v[i];
        }
        return productoPunto;
    }

    /** Producto cruz (Producto vectorial en R3): u x v */
    public static double[] calcularProductoCruz(double[] u, double[] v) {
        if (u.length != 3 || v.length != 3) {
            throw new IllegalArgumentException("El producto cruz está definido estrictamente para vectores en R3.");
        }
        double[] resultado = new double[3];
        // Componente i: u2*v3 - u3*v2
        resultado[0] = u[1] * v[2] - u[2] * v[1];
        // Componente j: u3*v1 - u1*v3
        resultado[1] = u[2] * v[0] - u[0] * v[2];
        // Componente k: u1*v2 - u2*v0
        resultado[2] = u[0] * v[1] - u[1] * v[0];

        return resultado;
    }

    /** Ángulo entre dos vectores: θ = arccos( (u · v) / (||u|| * ||v||) ) */
    public static double calcularAnguloEntreVectores(double[] u, double[] v) {
        double magU = calcularMagnitud(u);
        double magV = calcularMagnitud(v);

        if (magU == 0.0 || magV == 0.0) {
            throw new ArithmeticException("No se puede calcular el ángulo con un vector nulo.");
        }

        double productoPunto = calcularProductoPunto(u, v);
        double coseno = productoPunto / (magU * magV);

        // Control por errores de precisión de punto flotante para asegurar que esté en el rango [-1, 1]
        if (coseno > 1.0) coseno = 1.0;
        if (coseno < -1.0) coseno = -1.0;

        return Math.acos(coseno);
    }

    /** Proyección de un vector u sobre un vector v: proj_v(u) */
    public static double[] calcularProyeccion(double[] u, double[] v) {
        double magV = calcularMagnitud(v);
        if (magV == 0.0) {
            throw new ArithmeticException("No se puede proyectar sobre un vector nulo.");
        }

        double productoPunto = calcularProductoPunto(u, v);
        double escalar = productoPunto / (magV * magV);

        return multiplicarPorEscalar(v, escalar);
    }
}