package com.biomath3d.math.form;

/**
 * Catálogo completo de derivadas directas, inversas, logarítmicas e hiperbólicas.
 * Incluye soporte analítico mediante regla de la cadena y aproximación multivariable.
 */
public class FormularioDerivadas {

    // ==========================================
    //           DEFINICIÓN POR LÍMITE
    // ==========================================

    /** * Simulación de la definición matemática por límite usando Diferencias Centrales.
     * f'(x) ≈ (f(x + h) - f(x - h)) / (2h)
     */
    public static double definicionDerivadaPorLimite(java.util.function.Function<Double, Double> funcion, double x) {
        double h = 1e-5; // Incremento infinitesimal para alta precisión numérica
        return (funcion.apply(x + h) - funcion.apply(x - h)) / (2.0 * h);
    }

    // ==========================================
    //          REGLAS OPERACIONALES BASE
    // ==========================================

    public static double derivadaConstante(double c) {
        return 0.0;
    }

    public static double derivadaConstantePorVariable(double c) {
        return c;
    }

    /** d/dx (x^n) = n * x^(n-1) */
    public static double derivadaPotencia(double x, double n) {
        if (x == 0.0 && n - 1 < 0) {
            throw new ArithmeticException("Derivada indefinida en cero para potencias negativas.");
        }
        return n * Math.pow(x, n - 1.0);
    }

    public static double derivadaSumaResta(double du, double dv, double dw) {
        return du + dv + dw;
    }

    public static double derivadaVariablePorConstante(double c, double du) {
        return c * du;
    }

    /** d/dx (u * v) = u * dv + v * du */
    public static double derivadaProducto(double u, double du, double v, double dv) {
        return (u * dv) + (v * du);
    }

    /** d/dx (u * v * w) = du * v * w + u * dv * w + u * v * dw */
    public static double derivadaProductoTresVariables(double u, double du, double v, double dv, double w, double dw) {
        return (du * v * w) + (u * dv * w) + (u * v * dw);
    }

    /** d/dx (u / v) = (v * du - u * dv) / v² */
    public static double derivadaCociente(double u, double du, double v, double dv) {
        if (v == 0.0) {
            throw new ArithmeticException("División entre cero al evaluar la derivada del cociente.");
        }
        return ((v * du) - (u * dv)) / (v * v);
    }

    /** d/dx (u^n) = n * u^(n-1) * du */
    public static double derivadaPotenciaFuncion(double u, double du, double n) {
        return n * Math.pow(u, n - 1.0) * du;
    }

    public static double reglaCadena(double dF_du, double du_dx) {
        return dF_du * du_dx;
    }

    // ==========================================
    //         LOGARÍTMICAS Y EXPONENCIALES
    // ==========================================

    /** d/dx (ln u) = du / u */
    public static double derivadaLogaritmoNatural(double u, double du) {
        if (u <= 0.0) {
            throw new IllegalArgumentException("El argumento del logaritmo debe ser mayor a cero.");
        }
        return du / u;
    }

    /** d/dx (log_a u) = du / (u * ln a) */
    public static double derivadaLogaritmoBaseA(double u, double du, double a) {
        if (u <= 0.0 || a <= 0.0 || a == 1.0) {
            throw new IllegalArgumentException("Base o argumento del logaritmo inválidos.");
        }
        return du / (u * Math.log(a));
    }

    /** d/dx (e^u) = e^u * du */
    public static double derivadaExponencialE(double u, double du) {
        return Math.exp(u) * du;
    }

    /** d/dx (a^u) = a^u * ln(a) * du */
    public static double derivadaExponencialBaseA(double u, double du, double a) {
        if (a <= 0.0) {
            throw new IllegalArgumentException("La base exponencial 'a' debe ser positiva.");
        }
        return Math.pow(a, u) * Math.log(a) * du;
    }

    /** d/dx (u^v) = u^v * (dv * ln(u) + v * du / u) */
    public static double derivadaFuncionElevadaAFuncion(double u, double du, double v, double dv) {
        if (u <= 0.0) {
            throw new IllegalArgumentException("La base u debe ser mayor que cero para evaluar u^v.");
        }
        return Math.pow(u, v) * ((dv * Math.log(u)) + (v * du / u));
    }

    // ==========================================
    //          TRIGONOMETRÍA DIRECTA
    // ==========================================

    public static double derivadaSeno(double u, double du) {
        return Math.cos(u) * du;
    }

    public static double derivadaCoseno(double u, double du) {
        return -Math.sin(u) * du;
    }

    public static double derivadaTangente(double u, double du) {
        double sec = 1.0 / Math.cos(u);
        return (sec * sec) * du;
    }

    public static double derivadaCotangente(double u, double du) {
        double csc = 1.0 / Math.sin(u);
        return -(csc * csc) * du;
    }

    public static double derivadaSecante(double u, double du) {
        return Math.toDegrees(u) == 90 ? 0 : (1.0 / Math.cos(u)) * Math.tan(u) * du;
    }

    public static double derivadaCosecante(double u, double du) {
        double csc = 1.0 / Math.sin(u);
        double ctg = 1.0 / Math.tan(u);
        return -csc * ctg * du;
    }

    /** d/dx (versin u) = sen(u) * du */
    public static double derivadaVerseno(double u, double du) {
        return Math.sin(u) * du;
    }

    // ==========================================
    //          TRIGONOMETRÍA INVERSA
    // ==========================================

    public static double derivadaArcSeno(double u, double du) {
        if (Math.abs(u) >= 1.0) throw new IllegalArgumentException("Argumento fuera de rango [-1, 1] para arco seno.");
        return du / Math.sqrt(1.0 - (u * u));
    }

    public static double derivadaArcCoseno(double u, double du) {
        if (Math.abs(u) >= 1.0) throw new IllegalArgumentException("Argumento fuera de rango [-1, 1] para arco coseno.");
        return -du / Math.sqrt(1.0 - (u * u));
    }

    public static double derivadaArcTangente(double u, double du) {
        return du / (1.0 + (u * u));
    }

    public static double derivadaArcCotangente(double u, double du) {
        return -du / (1.0 + (u * u));
    }

    public static double derivadaArcSecante(double u, double du) {
        if (Math.abs(u) <= 1.0) throw new IllegalArgumentException("Argumento fuera de rango para arco secante.");
        return du / (Math.abs(u) * Math.sqrt(u * u - 1.0));
    }

    public static double derivadaArcCosecante(double u, double du) {
        if (Math.abs(u) <= 1.0) throw new IllegalArgumentException("Argumento fuera de rango para arco cosecante.");
        return -du / (Math.abs(u) * Math.sqrt(u * u - 1.0));
    }

    public static double derivadaArcVerseno(double u, double du) {
        if (u <= 0.0 || u >= 2.0) throw new IllegalArgumentException("Argumento fuera de rango para arco verseno.");
        return du / Math.sqrt(2.0 * u - u * u);
    }

    // ==========================================
    //          HIPERBÓLICAS DIRECTAS
    // ==========================================

    public static double derivadaSenoHiperbolico(double u, double du) {
        return Math.cosh(u) * du;
    }

    public static double derivadaCosenoHiperbolico(double u, double du) {
        return Math.sinh(u) * du;
    }

    public static double derivadaTangenteHiperbolica(double u, double du) {
        double sech = 1.0 / Math.cosh(u);
        return (sech * sech) * du;
    }

    public static double derivadaCotangenteHiperbolica(double u, double du) {
        double csch = 1.0 / Math.sinh(u);
        return -(csch * csch) * du;
    }

    public static double derivadaSecanteHiperbolica(double u, double du) {
        double sech = 1.0 / Math.cosh(u);
        return -sech * Math.tanh(u) * du;
    }

    public static double derivadaCosecanteHiperbolica(double u, double du) {
        double csch = 1.0 / Math.sinh(u);
        double ctgh = 1.0 / Math.tanh(u);
        return -csch * ctgh * du;
    }

    // ==========================================
    //          HIPERBÓLICAS INVERSAS
    // ==========================================

    public static double derivadaSenoHiperbolicoInverso(double u, double du) {
        return du / Math.sqrt(u * u + 1.0);
    }

    public static double derivadaCosenoHiperbolicoInverso(double u, double du) {
        if (u <= 1.0) throw new IllegalArgumentException("Argumento de cosh inverso debe ser > 1.");
        return du / Math.sqrt(u * u - 1.0);
    }

    public static double derivadaTangenteHiperbolicaInverso(double u, double du) {
        if (Math.abs(u) >= 1.0) throw new IllegalArgumentException("Argumento de tanh inverso debe estar en (-1, 1).");
        return du / (1.0 - (u * u));
    }

    public static double derivadaCotangenteHiperbolicaInverso(double u, double du) {
        if (Math.abs(u) <= 1.0) throw new IllegalArgumentException("Argumento de coth inverso debe estar fuera de [-1, 1].");
        return du / (1.0 - (u * u));
    }

    public static double derivadaSecanteHiperbolicaInverso(double u, double du) {
        if (u <= 0.0 || u >= 1.0) throw new IllegalArgumentException("Argumento de sech inverso debe estar en (0, 1).");
        return -du / (u * Math.sqrt(1.0 - u * u));
    }

    public static double derivadaCosecanteHiperbolicaInverso(double u, double du) {
        if (u == 0.0) throw new IllegalArgumentException("El argumento de csch inverso no puede ser cero.");
        return -du / (Math.abs(u) * Math.sqrt(1.0 + u * u));
    }

    // ========================
    //   DERIVADAS PARCIALES
    // ========================

    /**
     * Calcula la derivada parcial respecto a X de una superficie f(x,y) en un punto dado.
     * ∂f/∂x ≈ (f(x + h, y) - f(x - h, y)) / (2h)
     */
    public static double derivadaParcialX(java.util.function.BiFunction<Double, Double, Double> superficie, double x, double y) {
        double h = 1e-5;
        return (superficie.apply(x + h, y) - superficie.apply(x - h, y)) / (2.0 * h);
    }

    /**
     * Calcula la derivada parcial respecto a Y de una superficie f(x,y) en un punto dado.
     * ∂f/∂y ≈ (f(x, y + h) - f(x, y - h)) / (2h)
     */
    public static double derivadaParcialY(java.util.function.BiFunction<Double, Double, Double> superficie, double x, double y) {
        double h = 1e-5;
        return (superficie.apply(x, y + h) - superficie.apply(x, y - h)) / (2.0 * h);
    }
}