package com.biomath3d.math;

/**
 * Catálogo de operaciones analíticas y transformaciones lineales con matrices.
 * Implementado desde cero sin librerías externas.
 */
public class FormularioMatrices {

    /** Suma de matrices: A + B */
    public static double[][] sumarMatrices(double[][] a, double[][] b) {
        if (a.length != b.length || a[0].length != b[0].length) {
            throw new IllegalArgumentException("Las dimensiones de las matrices deben ser idénticas para sumarse.");
        }
        int filas = a.length;
        int columnas = a[0].length;
        double[][] resultado = new double[filas][columnas];

        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                resultado[i][j] = a[i][j] + b[i][j];
            }
        }
        return resultado;
    }

    /** Resta de matrices: A - B */
    public static double[][] restarMatrices(double[][] a, double[][] b) {
        if (a.length != b.length || a[0].length != b[0].length) {
            throw new IllegalArgumentException("Las dimensiones de las matrices deben ser idénticas para restarse.");
        }
        int filas = a.length;
        int columnas = a[0].length;
        double[][] resultado = new double[filas][columnas];

        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                resultado[i][j] = a[i][j] - b[i][j];
            }
        }
        return resultado;
    }

    /** Multiplicación por escalar: c * A */
    public static double[][] multiplicarPorEscalar(double[][] a, double c) {
        int filas = a.length;
        int columnas = a[0].length;
        double[][] resultado = new double[filas][columnas];

        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                resultado[i][j] = a[i][j] * c;
            }
        }
        return resultado;
    }

    /** Multiplicación de matrices (Producto matricial): A * B */
    public static double[][] multiplicarMatrices(double[][] a, double[][] b) {
        if (a[0].length != b.length) {
            throw new IllegalArgumentException("El número de columnas de A debe coincidir con las filas de B.");
        }
        int filasA = a.length;
        int columnasA = a[0].length;
        int columnasB = b[0].length;
        double[][] resultado = new double[filasA][columnasB];

        for (int i = 0; i < filasA; i++) {
            for (int j = 0; j < columnasB; j++) {
                double suma = 0.0;
                for (int k = 0; k < columnasA; k++) {
                    suma += a[i][k] * b[k][j];
                }
                resultado[i][j] = suma;
            }
        }
        return resultado;
    }

    /** Transpuesta de una matriz: A^T */
    public static double[][] obtenerTranspuesta(double[][] a) {
        int filas = a.length;
        int columnas = a[0].length;
        double[][] resultado = new double[columnas][filas];

        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                resultado[j][i] = a[i][j];
            }
        }
        return resultado;
    }

    /** Determinante de una matriz cuadrada (soporte general o mediante cofactores) */
    public static double calcularDeterminante(double[][] a) {
        if (a.length != a[0].length) {
            throw new IllegalArgumentException("El determinante solo está definido para matrices cuadradas.");
        }
        int n = a.length;

        // Casos base
        if (n == 1) {
            return a[0][0];
        }
        if (n == 2) {
            return (a[0][0] * a[1][1]) - (a[0][1] * a[1][0]);
        }

        double determinante = 0.0;
        for (int j = 0; j < n; j++) {
            double[][] submatriz = obtenerSubmatriz(a, 0, j);
            // Signo alternante: (-1)^j elevado al índice
            double signo = (j % 2 == 0) ? 1.0 : -1.0;
            determinante += signo * a[0][j] * calcularDeterminante(submatriz);
        }
        return determinante;
    }

    /** Matriz de Cofactores o Adjunta */
    public static double[][] obtenerMatrizAdjunta(double[][] a) {
        if (a.length != a[0].length) {
            throw new IllegalArgumentException("La matriz adjunta solo se puede calcular en matrices cuadradas.");
        }
        int n = a.length;
        double[][] cofactores = new double[n][n];

        if (n == 1) {
            cofactores[0][0] = 1.0;
            return cofinanciarMatrizOTranspuestaSiEsRequerido(cofactores);
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                double[][] submatriz = obtenerSubmatriz(a, i, j);
                double signo = ((i + j) % 2 == 0) ? 1.0 : -1.0;
                cofactores[i][j] = signo * calcularDeterminante(submatriz);
            }
        }
        // Nota técnica: La matriz adjunta tradicional es la transpuesta de la matriz de cofactores
        return obtenerTranspuesta(cofactores);
    }

    /** Inversa de una matriz utilizando la Adjunta: A⁻¹ = Adj(A)^T / det(A) */
    public static double[][] obtenerInversa(double[][] a) {
        double determinante = calcularDeterminante(a);
        if (determinante == 0.0) {
            throw new ArithmeticException("La matriz es singular (determinante cero), no tiene inversa.");
        }

        double[][] adjunta = obtenerMatrizAdjunta(a);
        int n = a.length;
        double[][] inversa = new double[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                inversa[i][j] = adjunta[i][j] / determinante;
            }
        }
        return inversa;
    }

    // ==========================================
    //    MATRICES DE TRANSFORMACIÓN 3D
    // ==========================================

    /** Genera una matriz de identidad de tamaño N x N */
    public static double[][] generarMatrizIdentidad(int dimension) {
        double[][] identidad = new double[dimension][dimension];
        for (int i = 0; i < dimension; i++) {
            identidad[i][i] = 1.0;
        }
        return identidad;
    }

    /** Matriz de rotación pura en el eje X para renderizado 3D */
    public static double[][] matrizRotacionX(double anguloRadianes) {
        double cos = Math.cos(anguloRadianes);
        double sin = Math.sin(anguloRadianes);

        return new double[][]{
                {1.0,  0.0,  0.0},
                {0.0,  cos, -sin},
                {0.0,  sin,  cos}
        };
    }

    /** Matriz de rotación pura en el eje Y para renderizado 3D */
    public static double[][] matrizRotacionY(double anguloRadianes) {
        double cos = Math.cos(anguloRadianes);
        double sin = Math.sin(anguloRadianes);

        return new double[][]{
                { cos, 0.0, sin},
                { 0.0, 1.0, 0.0},
                {-sin, 0.0, cos}
        };
    }

    /** Matriz de rotación pura en el eje Z para renderizado 3D */
    public static double[][] matrizRotacionZ(double anguloRadianes) {
        double cos = Math.cos(anguloRadianes);
        double sin = Math.sin(anguloRadianes);

        return new double[][]{
                {cos, -sin, 0.0},
                {sin,  cos, 0.0},
                {0.0,  0.0, 1.0}
        };
    }

    /**
     * Elimina una fila y una columna específica de una matriz para calcular menores.
     */
    private static double[][] obtenerSubmatriz(double[][] matriz, int filaEliminar, int colEliminar) {
        int n = matriz.length;
        double[][] submatriz = new double[n - 1][n - 1];
        int r = 0;

        for (int i = 0; i < n; i++) {
            if (i == filaEliminar) continue;
            int c = 0;
            for (int j = 0; j < n; j++) {
                if (j == colEliminar) continue;
                submatriz[r][c] = matriz[i][j];
                c++;
            }
            r++;
        }
        return submatriz;
    }

    private static double[][] cofinanciarMatrizOTranspuestaSiEsRequerido(double[][] m) {
        return m;
    }
}