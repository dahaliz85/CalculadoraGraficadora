package com.biomath3d.math.form;

/**
 * Operaciones algebraicas, leyes de exponentes, logaritmos y productos notables.
 * Implementado de forma nativa para BioMath 3D.
 */
public class FormularioAlgebraLogaritmos {

    // ==========================================
    //               VALOR ABSOLUTO
    // ==========================================

    public static double valorAbsoluto(double a) {
        return Math.abs(a);
    }

    /**
     * Valida la desigualdad triangular: |a + b| <= |a| + |b|
     */
    public static boolean cumpleDesigualdadTriangular(double a, double b) {
        return Math.abs(a + b) <= (Math.abs(a) + Math.abs(b));
    }

    // ==========================================
    //                 EXPONENCIALES
    // ==========================================

    /** a^m * a^n = a^(m+n) */
    public static double productoExponentes(double a, double m, double n) {
        return Math.pow(a, m + n);
    }

    /** a^m / a^n = a^(m-n) */
    public static double cocienteExponentes(double a, double m, double n) {
        if (a == 0.0 && m - n <= 0) {
            throw new ArithmeticException("División entre cero detectada al evaluar exponentes.");
        }
        return Math.pow(a, m - n);
    }

    /** (a^m)^n = a^(m*n) */
    public static double potenciaDePotencia(double a, double m, double n) {
        return Math.pow(a, m * n);
    }

    /** (a*b)^n = a^n * b^n */
    public static double potenciaProducto(double a, double b, double n) {
        return Math.pow(a * b, n);
    }

    /** (a/b)^n = a^n / b^n */
    public static double potenciaCociente(double a, double b, double n) {
        if (b == 0.0) {
            throw new ArithmeticException("División entre cero: la base b no puede ser cero.");
        }
        return Math.pow(a / b, n);
    }

    /** a^-n = 1 / a^n */
    public static double potenciaNegativa(double a, double n) {
        if (a == 0.0 && n >= 0) {
            throw new ArithmeticException("División entre cero: 0 elevado a una potencia negativa es indefinido.");
        }
        return Math.pow(a, -n);
    }

    /** a^(m/n) = n_raíz(a^m) */
    public static double potenciaFraccionaria(double a, double m, double n) {
        if (n == 0.0) {
            throw new ArithmeticException("El índice de la raíz (denominador n) no puede ser cero.");
        }
        if (a < 0.0 && n % 2 == 0) {
            throw new IllegalArgumentException("Raíz par de un número negativo produce un resultado imaginario.");
        }
        return Math.pow(a, m / n);
    }

    // ==========================================
    //                 LOGARITMOS
    // ==========================================

    /** log_a(x) = ln(x) / ln(a) */
    public static double definicionLogaritmo(double a, double x) {
        if (a <= 0.0 || a == 1.0) {
            throw new IllegalArgumentException("La base del logaritmo 'a' debe ser mayor que 0 y diferente de 1.");
        }
        if (x <= 0.0) {
            throw new IllegalArgumentException("El argumento 'x' del logaritmo debe ser estrictamente mayor que 0.");
        }
        return Math.log(x) / Math.log(a);
    }

    /** log_a(m * n) = log_a(m) + log_a(n) */
    public static double logbaritmoProducto(double a, double m, double n) {
        return definicionLogaritmo(a, m) + definicionLogaritmo(a, n);
    }

    /** log_a(m / n) = log_a(m) - log_a(n) */
    public static double logaritmoCociente(double a, double m, double n) {
        return definicionLogaritmo(a, m) - definicionLogaritmo(a, n);
    }

    /** log_a(m^n) = n * log_a(m) */
    public static double logaritmoPotencia(double a, double m, double n) {
        return n * definicionLogaritmo(a, m);
    }

    /** Cambio de base estándar a logaritmo natural: ln(n) / ln(a) */
    public static double logaritmoCambioBase(double a, double n) {
        return definicionLogaritmo(a, n);
    }

    /** log_a(a) = 1 */
    public static double logaritmoBaseIdentidad(double a) {
        if (a <= 0.0 || a == 1.0) {
            throw new IllegalArgumentException("Base inválida.");
        }
        return 1.0;
    }

    /** log_a(1) = 0 */
    public static double logaritmoDeUno(double a) {
        if (a <= 0.0 || a == 1.0) {
            throw new IllegalArgumentException("Base inválida.");
        }
        return 0.0;
    }

    // ==========================================
    //             PRODUCTOS NOTABLES
    // ==========================================

    /** a * (c + d) = a*c + a*d */
    public static double monomioPorPolinomio(double a, double c, double d) {
        return (a * c) + (a * d);
    }

    /** (a + b)(a - b) = a² - b² */
    public static double diferenciaCuadrados(double a, double b) {
        return (a * a) - (b * b);
    }

    /** (x + a)(x + b) = x² + (a+b)x + a*b */
    public static double productoBinomiosTerminoComun(double x, double a, double b) {
        return (x * x) + ((a + b) * x) + (a * b);
    }

    /** (a ± b)² = a² ± 2ab + b² */
    public static double binomioAlCuadrado(double a, double b, boolean esSuma) {
        double terminoMedio = 2.0 * a * b;
        return (a * a) + (esSuma ? terminoMedio : -terminoMedio) + (b * b);
    }

    /** (a ± b)³ = a³ ± 3a²b + 3ab² ± b³ */
    public static double binomioAlCubo(double a, double b, boolean esSuma) {
        double t1 = Math.pow(a, 3);
        double t2 = 3.0 * (a * a) * b;
        double t3 = 3.0 * a * (b * b);
        double t4 = Math.pow(b, 3);

        return esSuma ? (t1 + t2 + t3 + t4) : (t1 - t2 + t3 - t4);
    }

    /** (a + b)(a² - ab + b²) = a³ + b³  u  (a - b)(a² + ab + b²) = a³ - b³ */
    public static double sumaDiferenciaCubos(double a, double b, boolean esSuma) {
        double t1 = Math.pow(a, 3);
        double t2 = Math.pow(b, 3);
        return esSuma ? (t1 + t2) : (t1 - t2);
    }

    /** (a + b + c)² = a² + b² + c² + 2ab + 2ac + 2bc */
    public static double polinomioAlCuadrado(double a, double b, double c) {
        return (a * a) + (b * b) + (c * c) + (2.0 * a * b) + (2.0 * a * c) + (2.0 * b * c);
    }

    // ==========================================
    //        BINOMIO A LA POTENCIA N (PASCAL)
    // ==========================================

    /**
     * Genera los coeficientes de la fila 'n' del Triángulo de Pascal.
     * Ejemplo para n = 3: devuelve [1.0, 3.0, 3.0, 1.0]
     */
    public static double[] obtenerFilaPascal(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("La potencia no puede ser negativa.");
        }

        double[] fila = new double[n + 1];
        fila[0] = 1.0; // El primer elemento siempre es 1

        for (int i = 1; i <= n; i++) {
            // Propiedad analítica: cada término se calcula a partir del anterior
            // Coeficiente_k = Coeficiente_(k-1) * (n - k + 1) / k
            fila[i] = fila[i - 1] * (n - i + 1) / i;
        }
        return fila;
    }

    /**
     * Resuelve de forma general (a ± b)^n usando los coeficientes del Triángulo de Pascal.
     * Soporta cualquier potencia entera n >= 0.
     */
    public static double binomioALaPotenciaN(double a, double b, int n, boolean esSuma) {
        // 1. Obtenemos los coeficientes de Pascal para la potencia solicitada
        double[] coeficientes = obtenerFilaPascal(n);
        double resultado = 0.0;

        // 2. Desarrollamos la sumatoria del Binomio de Newton: Σ (n sobre k) * a^(n-k) * b^k
        for (int k = 0; k <= n; k++) {
            double terminoA = Math.pow(a, n - k);
            double terminoB = Math.pow(b, k);

            // Si es una resta (esSuma = false), los signos se alternan:
            // El segundo término (k=1) es negativo, el tercero (k=2) positivo, etc.
            double signo = (esSuma || k % 2 == 0) ? 1.0 : -1.0;

            resultado += signo * coeficientes[k] * terminoA * terminoB;
        }

        return resultado;
    }

    // ==========================================
    //              SUMAS Y PRODUCTOS
    // ==========================================

    public static double sumatoriaConstante(double c, int n) {
        return c * n;
    }

    public static double sumatoriaLineal(int n) {
        return (n * (n + 1)) / 2.0;
    }

    public static double sumatoriaCuadrados(int n) {
        return (n * (n + 1) * (2 * n + 1)) / 6.0;
    }

    public static double sumatoriaCubos(int n) {
        return Math.pow((n * (n + 1)) / 2.0, 2);
    }

    /** Suma de i⁴ de 1 a n = (n(n+1)(2n+1)(3n²+3n-1)) / 30 */
    public static double sumatoriaCuartaPotencia(int n) {
        return (n * (n + 1) * (2 * n + 1) * (3.0 * (n * n) + 3.0 * n - 1.0)) / 30.0;
    }

    public static double productoríaConstante(double c, int n) {
        return Math.pow(c, n);
    }
}