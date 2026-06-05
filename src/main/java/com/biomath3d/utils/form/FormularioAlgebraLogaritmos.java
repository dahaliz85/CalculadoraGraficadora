package com.biomath3d.utils.form;

/**
 * Operaciones algebraicas, leyes de exponentes, logaritmos y productos notables.
 */
public class FormularioAlgebraLogaritmos {

    // ==========================================
    //               VALOR ABSOLUTO
    // ==========================================
    public static double valorAbsoluto(double a) { return Math.abs(a); }
    public static boolean cumpleDesigualdadTriangular(double a, double b) { return true; }

    // ==========================================
    //                 EXPONENCIALES
    // ==========================================
    public static double productoExponentes(double a, double m, double n) { return 0.0; } // a^m * a^n
    public static double cocienteExponentes(double a, double m, double n) { return 0.0; } // a^m / a^n
    public static double potenciaDePotencia(double a, double m, double n) { return 0.0; } // (a^m)^n
    public static double potenciaProducto(double a, double b, double n) { return 0.0; }  // (a*b)^n
    public static double potenciaCociente(double a, double b, double n) { return 0.0; }  // (a/b)^n
    public static double potenciaNegativa(double a, double n) { return 0.0; }          // a^-n
    public static double potenciaFraccionaria(double a, double m, double n) { return 0.0; } // a^(m/n)

    // ==========================================
    //                 LOGARITMOS
    // ==========================================
    public static double definicionLogaritmo(double a, double x) { return 0.0; } // log_a(N)
    public static double logaritmoProducto(double a, double m, double n) { return 0.0; }
    public static double logaritmoCociente(double a, double m, double n) { return 0.0; }
    public static double logaritmoPotencia(double a, double m, double n) { return 0.0; }
    public static double logaritmoCambioBase(double a, double n) { return 0.0; }
    public static double logaritmoBaseIdentidad(double a) { return 1.0; }
    public static double logaritmoDeUno(double a) { return 0.0; }

    // ==========================================
    //             PRODUCTOS NOTABLES
    // ==========================================
    public static double monomioPorPolinomio(double a, double c, double d) { return 0.0; }
    public static double diferenciaCuadrados(double a, double b) { return 0.0; }
    public static double productoBinomiosTerminoComun(double x, double a, double b) { return 0.0; }
    public static double binomioAlCuadrado(double a, double b, boolean esSuma) { return 0.0; }
    public static double binomioAlCubo(double a, double b, boolean esSuma) { return 0.0; }
    public static double sumaDiferenciaCubos(double a, double b, boolean esSuma) { return 0.0; }
    public static double polinomioAlCuadrado(double a, double b, double c) { return 0.0; }

    // ==========================================
    //              SUMAS Y PRODUCTOS
    // ==========================================
    public static double sumatoriaConstante(double c, int n) { return c * n; }
    public static double sumatoriaLineal(int n) { return (n * (n + 1)) / 2.0; }
    public static double sumatoriaCuadrados(int n) { return (n * (n + 1) * (2 * n + 1)) / 6.0; }
    public static double sumatoriaCubos(int n) { return Math.pow((n * (n + 1)) / 2.0, 2); }
    public static double sumatoriaCuartaPotencia(int n) { return 0.0; }
    public static double productoríaConstante(double c, int n) { return Math.pow(c, n); }
}