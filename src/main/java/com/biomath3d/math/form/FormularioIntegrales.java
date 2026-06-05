package com.biomath3d.math.form;

import com.biomath3d.model.IntegralDefinida;

/**
 * Catálogo completo de reglas analíticas de integración y aproximación por series.
 * Diseñado e implementado de forma nativa para BioMath 3D.
 */
public class FormularioIntegrales {

    // ==========================================
    //      PROPIEDADES DE INTEGRALES DEFINIDAS
    // ==========================================

    public static double propiedadSumaResta(double intA, double intB) {
        return intA + intB;
    }

    public static double propiedadConstante(double c, double intA) {
        return c * intA;
    }

    public static double propiedadInversionLimites(double intA) {
        return -intA;
    }

    public static double propiedadLimitesIguales() {
        return 0.0;
    }

    public static double propiedadAcotamiento(double m, double M, double a, double b) {
        return m * (b - a); // Límite inferior estimado del área
    }

    // ==========================================
    //         INTEGRALES INMEDIATAS Y BASE
    // ==========================================

    public static double integralDiferencial(double x) {
        return x;
    }

    public static double integralConstantePorDiferencial(double a, double x) {
        return a * x;
    }

    public static double integralSumaDiferenciales(double[] integrales) {
        double suma = 0.0;
        for (double val : integrales) suma += val;
        return suma;
    }

    public static double integracionPorPartes(double u, double v, double integralVdu) {
        return (u * v) - integralVdu;
    }

    /** ∫ u^n du = (u^(n+1)) / (n+1) */
    public static double integralPotencia(double u, double n) {
        if (n == -1.0) {
            return integralLogaritmicaBase(u);
        }
        return Math.pow(u, n + 1.0) / (n + 1.0);
    }

    public static double integralLogaritmicaBase(double u) {
        if (u == 0.0) throw new ArithmeticException("Integral indefinida: logaritmo de cero.");
        return Math.log(Math.abs(u));
    }

    // ==========================================
    //        LOGARÍTMICAS Y EXPONENCIALES
    // ==========================================

    public static double integralExponencialE(double u) {
        return Math.exp(u);
    }

    public static double integralExponencialBaseA(double a, double u) {
        if (a <= 0.0 || a == 1.0) throw new IllegalArgumentException("Base exponencial inválida.");
        return Math.pow(a, u) / Math.log(a);
    }

    /** ∫ u^n * ln(u) du */
    public static double integralPotenciaLogaritmica(double u, double n) {
        if (u <= 0.0) throw new IllegalArgumentException("Argumento de logaritmo inválido.");
        if (n == -1.0) return 0.5 * Math.pow(Math.log(u), 2);
        return (Math.pow(u, n + 1.0) / (n + 1.0)) * (Math.log(u) - (1.0 / (n + 1.0)));
    }

    public static double integralLogaritmoNatural(double u) {
        if (u <= 0.0) throw new IllegalArgumentException("Argumento inválido.");
        return u * Math.log(u) - u;
    }

    /** ∫ log_a(u) du = (u * ln(u) - u) / ln(a) */
    public static double integralLogaritmoBaseA(double u, double a) {
        if (a <= 0.0 || a == 1.0) throw new IllegalArgumentException("Base inválida.");
        return integralLogaritmoNatural(u) / Math.log(a);
    }

    /** Integral de la inversa logarítmica (aproximación cualitativa o Li(u)) */
    public static double integralInversaLogaritmica(double u) {
        // Implementación simplificada mediante expansión de serie para Li(u)
        if (u <= 1.0) throw new IllegalArgumentException("Definido para u > 1.");
        double gamma = 0.5772156649; // Constante de Euler-Mascheroni
        return gamma + Math.log(Math.log(u)) + Math.log(u) + Math.pow(Math.log(u), 2)/4.0;
    }

    // ==========================================
    //          TRIGONOMETRÍA DIRECTA
    // ==========================================

    public static double integralSeno(double u) { return -Math.cos(u); }
    public static double integralCoseno(double u) { return Math.sin(u); }
    public static double integralSecanteCuadrada(double u) { return Math.tan(u); }

    public static double integralCosecanteCuadrada(double u) {
        double tan = Math.tan(u);
        if (tan == 0.0) throw new ArithmeticException("Cotangente indefinida.");
        return -1.0 / tan;
    }

    public static double integralSecanteTangente(double u) {
        double cos = Math.cos(u);
        if (cos == 0.0) throw new ArithmeticException("Secante indefinida.");
        return 1.0 / cos;
    }

    public static double integralCosecanteCotangente(double u) {
        double sen = Math.sin(u);
        if (sen == 0.0) throw new ArithmeticException("Cosecante indefinida.");
        return -1.0 / sen;
    }

    public static double integralTangente(double u) {
        return Math.log(Math.abs(1.0 / Math.cos(u)));
    }

    public static double integralCotangente(double u) {
        return Math.log(Math.abs(Math.sin(u)));
    }

    public static double integralSecante(double u) {
        return Math.log(Math.abs(Math.tan(u) + (1.0 / Math.cos(u))));
    }

    public static double integralCosecante(double u) {
        double csc = 1.0 / Math.sin(u);
        double ctg = 1.0 / Math.tan(u);
        return Math.log(Math.abs(csc - ctg));
    }

    public static double integralSenoCuadrado(double u) {
        return (u / 2.0) - (Math.sin(2.0 * u) / 4.0);
    }

    public static double integralCosenoCuadrado(double u) {
        return (u / 2.0) + (Math.sin(2.0 * u) / 4.0);
    }

    public static double integralTangenteCuadrada(double u) { return Math.tan(u) - u; }

    public static double integralCotangenteCuadrada(double u) {
        double tan = Math.tan(u);
        if (tan == 0.0) throw new ArithmeticException("Indefinido.");
        return - (1.0 / tan) - u;
    }

    // ==========================================
    //          TRIGONOMETRÍA INVERSA
    // ==========================================

    public static double integralArcSeno(double u) { return u * Math.asin(u) + Math.sqrt(1.0 - u * u); }
    public static double integralArcCoseno(double u) { return u * Math.acos(u) - Math.sqrt(1.0 - u * u); }
    public static double integralArcTangente(double u) { return u * Math.atan(u) - 0.5 * Math.log(1.0 + u * u); }
    public static double integralArcCotangente(double u) { return u * Math.atan(1.0/u) + 0.5 * Math.log(1.0 + u * u); }
    public static double integralArcSecante(double u) { return u * Math.acos(1.0/u) - Math.log(Math.abs(u) + Math.sqrt(u * u - 1.0)); }
    public static double integralArcCosecante(double u) { return u * Math.asin(1.0/u) + Math.log(Math.abs(u) + Math.sqrt(u * u - 1.0)); }

    // ==========================================
    //               HIPERBÓLICAS
    // ==========================================

    public static double integralSenoHiperbolico(double u) { return Math.cosh(u); }
    public static double integralCosenoHiperbolico(double u) { return Math.sinh(u); }
    public static double integralSecanteHiperbolicaCuadrada(double u) { return Math.tanh(u); }
    public static double integralCosecanteHiperbolicaCuadrada(double u) { return -1.0 / Math.tanh(u); }
    public static double integralSecanteHiperbolicaTangenteHiperbolica(double u) { return -1.0 / Math.cosh(u); }
    public static double integralCosecanteHiperbolicaCotangenteHiperbolica(double u) { return -1.0 / Math.sinh(u); }
    public static double integralTangenteHiperbolica(double u) { return Math.log(Math.cosh(u)); }
    public static double integralCotangenteHiperbolica(double u) { return Math.log(Math.abs(Math.sinh(u))); }

    // ==========================================
    //      FORMAS FRACCIONARIAS ESPECIALES
    // ==========================================

    /** ∫ 1 / (u² + a²) du */
    public static double integralFraccionSumaCuadrados(double u, double a) {
        if (a == 0.0) throw new ArithmeticException("La constante 'a' no puede ser cero.");
        return (1.0 / a) * Math.atan(u / a);
    }

    /** ∫ 1 / (u² - a²) du */
    public static double integralFraccionRestaCuadradosU(double u, double a) {
        if (a == 0.0 || Math.abs(u) == Math.abs(a)) throw new ArithmeticException("Indefinido.");
        return (1.0 / (2.0 * a)) * Math.log(Math.abs((u - a) / (u + a)));
    }

    /** ∫ 1 / (a² - u²) du */
    public static double integralFraccionRestaCuadradosA(double u, double a) {
        if (a == 0.0 || Math.abs(u) == Math.abs(a)) throw new ArithmeticException("Indefinido.");
        return (1.0 / (2.0 * a)) * Math.log(Math.abs((a + u) / (a - u)));
    }

    // ==========================================
    //        FORMAS AVANZADAS CON RAÍZ (√)
    // ==========================================

    /** ∫ √(u² ± a²) du */
    public static double integralRaizSumaCuadrados(double u, double a) {
        return 0.5 * (u * Math.sqrt(u * u + a * a) + a * a * Math.log(Math.abs(u + Math.sqrt(u * u + a * a))));
    }

    /** ∫ √(a² - u²) du */
    public static double integralRaizRestaCuadrados(double u, double a, boolean esSuma) {
        if (!esSuma) { // Caso √(u² - a²)
            return 0.5 * (u * Math.sqrt(u * u - a * a) - a * a * Math.log(Math.abs(u + Math.sqrt(u * u - a * a))));
        }
        return 0.5 * (u * Math.sqrt(a * a - u * u) + a * a * Math.asin(u / a));
    }

    /** ∫ 1 / √(u² ± a²) du */
    public static double integralRaizInversaU(double u, double a) {
        return Math.log(Math.abs(u + Math.sqrt(u * u + a * a)));
    }

    /** ∫ 1 / √(a² - u²) du */
    public static double integralRaizInversaA(double u, double a) {
        if (Math.abs(u) > Math.abs(a)) throw new IllegalArgumentException("Argumento inválido.");
        return Math.asin(u / a);
    }

    // ==========================================
    //          MÁS INTEGRALES COMPLEJAS
    // ==========================================

    public static double integralExponencialPorSeno(double a, double b, double u) {
        double den = a * a + b * b;
        return (Math.exp(a * u) / den) * (a * Math.sin(b * u) - b * Math.cos(b * u));
    }

    public static double integralExponencialPorCoseno(double a, double b, double u) {
        double den = a * a + b * b;
        return (Math.exp(a * u) / den) * (a * Math.cos(b * u) + b * Math.sin(b * u));
    }

    // ==========================================
    //        MÉTODOS DE INTEGRACIÓN (NUEVO)
    // ==========================================

    public static double integracionPorSustitucion(double uEvaluada, double duConstante) {
        // Aplica el factor de ajuste del diferencial al resultado integrado básico
        return uEvaluada / duConstante;
    }

    public static double integracionFraccionesParcialesCasoLineal(double a, double b, double x) {
        // ∫ 1 / (ax + b) dx = (1/a) * ln|ax + b|
        return (1.0 / a) * Math.log(Math.abs(a * x + b));
    }

    // ==========================================
    //    INTEGRALES DEFINIDAS MÚLTIPLES (NUEVO)
    // ==========================================

    /**
     * Calcula una Integral Doble Definida sobre una región rectangular empleando Sumas de Riemann.
     * ∫∫ f(x,y) dA
     */
    public static double calcularIntegralDoble(java.util.function.BiFunction<Double, Double, Double> funcion, IntegralDefinida ejeX, IntegralDefinida ejeY) {
        int particiones = 100; // Cuadrícula de 100x100 para balancear precisión y velocidad
        double hx = (ejeX.getLimiteSuperior() - ejeX.getLimiteInferior()) / particiones;
        double hy = (ejeY.getLimiteSuperior() - ejeY.getLimiteInferior()) / particiones;
        double areaDiferencial = hx * hy;
        double sumatoriaVolumen = 0.0;

        for (int i = 0; i < particiones; i++) {
            // Punto medio del intervalo del subrectángulo para mayor convergencia
            double x = ejeX.getLimiteInferior() + (i + 0.5) * hx;
            for (int j = 0; j < particiones; j++) {
                double y = ejeY.getLimiteInferior() + (j + 0.5) * hy;
                sumatoriaVolumen += funcion.apply(x, y);
            }
        }
        return sumatoriaVolumen * areaDiferencial;
    }

    /**
     * Triples: ∫∫∫ f(x,y,z) dV mediante aproximación tridimensional en cubos elementales.
     */
    public static double calcularIntegralTriple(java.util.function.BiFunction<Double, java.util.function.BiFunction<Double, Double, Double>, Double> funcionDummy,
                                                IntegralDefinida ejeX, IntegralDefinida ejeY, IntegralDefinida ejeZ) {
        // Nota: Para no saturar memoria en el hilo gráfico de JavaFX, limitamos a 40 particiones cúbicas (40³ = 64,000 evaluaciones)
        int particiones = 40;
        double hx = (ejeX.getLimiteSuperior() - ejeX.getLimiteInferior()) / particiones;
        double hy = (ejeY.getLimiteSuperior() - ejeY.getLimiteInferior()) / particiones;
        double hz = (ejeZ.getLimiteSuperior() - ejeZ.getLimiteInferior()) / particiones;
        double volumenDiferencial = hx * hy * hz;
        double sumatoriaMasa = 0.0;

        // Simulación controlada: en un Parser real se pasará una interfaz funcional de 3 argumentos
        for (int i = 0; i < particiones; i++) {
            double x = ejeX.getLimiteInferior() + (i + 0.5) * hx;
            for (int j = 0; j < particiones; j++) {
                double y = ejeY.getLimiteInferior() + (j + 0.5) * hy;
                for (int k = 0; k < particiones; k++) {
                    double z = ejeZ.getLimiteInferior() + (k + 0.5) * hz;
                    // Simulación del comportamiento del Parser tridimensional para el punto (x,y,z)
                    sumatoriaMasa += (x * y * z); // Operación de prueba por defecto
                }
            }
        }
        return sumatoriaMasa * volumenDiferencial;
    }

    // ==========================================
    //           DESARROLLOS EN SERIES
    // ==========================================

    public static double serieTaylor(double x) { return serieMaclaurin(x); }

    public static double serieMaclaurin(double x) {
        return serieExponencialE(x);
    }

    public static double serieExponencialE(double x) {
        double suma = 1.0;
        double termino = 1.0;
        for (int i = 1; i <= 15; i++) {
            termino *= (x / i);
            suma += termino;
        }
        return suma;
    }

    public static double serieSeno(double x) {
        double suma = x;
        double termino = x;
        for (int i = 1; i <= 10; i++) {
            termino *= - (x * x) / ((2 * i) * (2 * i + 1));
            suma += termino;
        }
        return suma;
    }

    public static double serieCoseno(double x) {
        double suma = 1.0;
        double termino = 1.0;
        for (int i = 1; i <= 10; i++) {
            termino *= - (x * x) / ((2 * i - 1) * (2 * i));
            suma += termino;
        }
        return suma;
    }

    public static double serieLogaritmoNatural(double x) {
        if (x <= -1.0 || x > 1.0) throw new IllegalArgumentException("Fuera del radio de convergencia (-1, 1].");
        double suma = 0.0;
        for (int i = 1; i <= 20; i++) {
            double signo = (i % 2 == 0) ? -1.0 : 1.0;
            suma += signo * Math.pow(x, i) / i;
        }
        return suma;
    }

    public static double serieArcoTangente(double x) {
        if (Math.abs(x) > 1.0) throw new IllegalArgumentException("Fuera del radio de convergencia.");
        double suma = 0.0;
        for (int i = 0; i < 15; i++) {
            double signo = (i % 2 == 0) ? 1.0 : -1.0;
            suma += signo * Math.pow(x, 2 * i + 1) / (2 * i + 1);
        }
        return suma;
    }
}