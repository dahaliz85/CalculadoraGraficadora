package com.biomath3d.math;

/**
 * Identidades trigonométricas planas, circulares, hiperbólicas, límites notables,
 * conversiones angulares y geometría de sustitución trigonométrica.
 * Implementado de forma nativa para BioMath 3D.
 */
public class FormularioIdentidadesTrigonometricas {

    // ==========================================
    //          TRIGONOMETRÍA PLANA BASE
    // ==========================================

    public static double senDefinicion(double co, double hip) {
        if (hip == 0.0) throw new ArithmeticException("La hipotenusa no puede ser cero.");
        return co / hip;
    }

    public static double cosDefinicion(double ca, double hip) {
        if (hip == 0.0) throw new ArithmeticException("La hipotenusa no puede ser cero.");
        return ca / hip;
    }

    public static double tgDefinicion(double co, double ca) {
        if (ca == 0.0) throw new ArithmeticException("El cateto adyacente no puede ser cero (Tangente indefinida).");
        return co / ca;
    }

    public static double cscDefinicion(double co, double hip) {
        if (co == 0.0) throw new ArithmeticException("El cateto opuesto no puede ser cero (Cosecante indefinida).");
        return hip / co;
    }

    public static double secDefinicion(double ca, double hip) {
        if (ca == 0.0) throw new ArithmeticException("El cateto adyacente no puede ser cero (Secante indefinida).");
        return hip / ca;
    }

    public static double ctgDefinicion(double co, double ca) {
        if (co == 0.0) throw new ArithmeticException("El cateto opuesto no puede ser cero (Cotangente indefinida).");
        return ca / co;
    }

    /** * Tabla de Ángulos Notables (0, 30, 45, 60, 90 grados) con valores exactos basados en radicales
     */
    public static double obtenerValorAngularExacto(String funcion, int grados) {
        String func = funcion.trim().toLowerCase();
        switch (grados) {
            case 0:
                if (func.equals("sin") || func.equals("tan")) return 0.0;
                if (func.equals("cos") || func.equals("sec")) return 1.0;
                throw new ArithmeticException("Función indefinida para 0 grados.");
            case 30:
                if (func.equals("sin")) return 0.5;
                if (func.equals("cos")) return Math.sqrt(3.0) / 2.0;
                if (func.equals("tan")) return Math.sqrt(3.0) / 3.0;
                break;
            case 45:
                if (func.equals("sin") || func.equals("cos")) return Math.sqrt(2.0) / 2.0;
                if (func.equals("tan")) return 1.0;
                break;
            case 60:
                if (func.equals("sin")) return Math.sqrt(3.0) / 2.0;
                if (func.equals("cos")) return 0.5;
                if (func.equals("tan")) return Math.sqrt(3.0);
                break;
            case 90:
                if (func.equals("sin") || func.equals("csc")) return 1.0;
                if (func.equals("cos")) return 0.0;
                if (func.equals("tan")) throw new ArithmeticException("Tangente de 90 grados es indefinida.");
                break;
            default:
                // Si no es un ángulo notable directo, se evalúa de forma estándar convirtiendo a radianes
                double rad = grados * (Math.PI / 180.0);
                if (func.equals("sin")) return Math.sin(rad);
                if (func.equals("cos")) return Math.cos(rad);
                if (func.equals("tan")) return Math.tan(rad);
        }
        return 0.0;
    }

    // Identidades Pitagóricas e Inversas Planas
    public static double sen2PlusCos2(double x) {
        return 1.0; // Identidad fundamental fundamental: sen²x + cos²x = 1
    }

    public static double onePlusCtg2(double x) {
        double sen = Math.sin(x);
        if (sen == 0.0) throw new ArithmeticException("Cosecante cuadrada indefinida en este punto.");
        return 1.0 / (sen * sen); // csc²x
    }

    public static double tg2PlusOne(double x) {
        double cos = Math.cos(x);
        if (cos == 0.0) throw new ArithmeticException("Secante cuadrada indefinida en este punto.");
        return 1.0 / (cos * cos); // sec²x
    }

    // Paridad y Periodicidad Plana
    public static double senParidad(double x) { return -Math.sin(x); } // sen(-x) = -sen(x)
    public static double cosParidad(double x) { return Math.cos(x); }   // cos(-x) = cos(x)
    public static double tgParidad(double x) { return -Math.tan(x); }   // tan(-x) = -tan(x)

    public static double DesplazamientoRadianesSeno(double x, int n) {
        return Math.sin(x + 2.0 * Math.PI * n);
    }

    public static double DesplazamientoRadianesCoseno(double x, int n) {
        return Math.cos(x + 2.0 * Math.PI * n);
    }

    // Ángulos Compuestos y Dobles Planos
    public static double senSumaRestaPlana(double a, double b, boolean esSuma) {
        // sen(a ± b) = sen(a)cos(b) ± cos(a)sen(b)
        return esSuma ? (Math.sin(a) * Math.cos(b) + Math.cos(a) * Math.sin(b))
                : (Math.sin(a) * Math.cos(b) - Math.cos(a) * Math.sin(b));
    }

    public static double cosSumaRestaPlana(double a, double b, boolean esSuma) {
        // cos(a ± b) = cos(a)cos(b) ∓ sen(a)sen(b)
        return esSuma ? (Math.cos(a) * Math.cos(b) - Math.sin(a) * Math.sin(b))
                : (Math.cos(a) * Math.cos(b) + Math.sin(a) * Math.sin(b));
    }

    public static double tgSumaRestaPlana(double a, double b, boolean esSuma) {
        // tan(a ± b) = (tan(a) ± tan(b)) / (1 ∓ tan(a)tan(b))
        double den = esSuma ? (1.0 - Math.tan(a) * Math.tan(b)) : (1.0 + Math.tan(a) * Math.tan(b));
        if (den == 0.0) throw new ArithmeticException("División entre cero: asíntota en la suma de tangentes.");
        return esSuma ? ((Math.tan(a) + Math.tan(b)) / den) : ((Math.tan(a) - Math.tan(b)) / den);
    }

    public static double senAnguloDoblePlano(double x) {
        return 2.0 * Math.sin(x) * Math.cos(x);
    }

    public static double cosAnguloDoblePlano(double x) {
        return (Math.cos(x) * Math.cos(x)) - (Math.sin(x) * Math.sin(x));
    }

    public static double tgAnguloDoblePlano(double x) {
        double den = 1.0 - (Math.tan(x) * Math.tan(x));
        if (den == 0.0) throw new ArithmeticException("Tangente de ángulo doble indefinida.");
        return (2.0 * Math.tan(x)) / den;
    }

    public static double senAnguloMedioPlano(double x) {
        return Math.sqrt((1.0 - Math.cos(x)) / 2.0);
    }

    public static double cosAnguloMedioPlano(double x) {
        return Math.sqrt((1.0 + Math.cos(x)) / 2.0);
    }

    public static double tgAnguloMedioPlano(double x) {
        double den = 1.0 + Math.cos(x);
        if (den == 0.0) throw new ArithmeticException("Tangente de ángulo medio indefinida.");
        return Math.sin(x) / den;
    }

    // Transformación de Productos a Sumas
    public static double productoSenoCoseno(double alpha, double beta) {
        return 0.5 * (Math.sin(alpha + beta) + Math.sin(alpha - beta));
    }

    public static double productoCosenoCoseno(double alpha, double beta) {
        return 0.5 * (Math.cos(alpha + beta) + Math.cos(alpha - beta));
    }

    public static double productoSenoSeno(double alpha, double beta) {
        return 0.5 * (Math.cos(alpha - beta) - Math.cos(alpha + beta));
    }

    // ==========================================
    //         IDENTIDADES HIPERBÓLICAS
    // ==========================================

    public static double cosh2MinusSinh2(double x) {
        return 1.0; // Identidad fundamental hiperbólica: cosh²x - sinh²x = 1
    }

    public static double oneMinusTgh2(double x) {
        double cosh = Math.cosh(x);
        return 1.0 / (cosh * cosh); // sech²x
    }

    public static double ctgh2MinusOne(double x) {
        double sinh = Math.sinh(x);
        if (sinh == 0.0) throw new ArithmeticException("Cosecante hiperbólica cuadrada indefinida en x = 0.");
        return 1.0 / (sinh * sinh); // csch²x
    }

    public static double sinhParidadHiperbolica(double x) { return -Math.sinh(x); }
    public static double coshParidadHiperbolica(double x) { return Math.cosh(x); }
    public static double tghParidadHiperbolica(double x) { return -Math.tanh(x); }

    public static double sinhSumaRestaHiperbolica(double x, double y, boolean esSuma) {
        return esSuma ? (Math.sinh(x) * Math.cosh(y) + Math.cosh(x) * Math.sinh(y))
                : (Math.sinh(x) * Math.cosh(y) - Math.cosh(x) * Math.sinh(y));
    }

    public static double coshSumaRestaHiperbolica(double x, double y, boolean esSuma) {
        return esSuma ? (Math.cosh(x) * Math.cosh(y) + Math.sinh(x) * Math.sinh(y))
                : (Math.cosh(x) * Math.cosh(y) - Math.sinh(x) * Math.sinh(y));
    }

    public static double tghSumaRestaHiperbolica(double x, double y, boolean esSuma) {
        double den = esSuma ? (1.0 + Math.tanh(x) * Math.tanh(y)) : (1.0 - Math.tanh(x) * Math.tanh(y));
        if (den == 0.0) throw new ArithmeticException("Tangente hiperbólica de la suma/resta indefinida.");
        return esSuma ? ((Math.tanh(x) + Math.tanh(y)) / den) : ((Math.tanh(x) - Math.tanh(y)) / den);
    }

    public static double sinhAnguloDobleHiperbolico(double x) {
        return 2.0 * Math.sinh(x) * Math.cosh(x);
    }

    public static double coshAnguloDobleHiperbolico(double x) {
        return (Math.cosh(x) * Math.cosh(x)) + (Math.sinh(x) * Math.sinh(x));
    }

    public static double tghAnguloDobleHiperbolico(double x) {
        return (2.0 * Math.tanh(x)) / (1.0 + Math.tanh(x) * Math.tanh(x));
    }

    public static double sinhReduccionPotencia(double x) {
        return (Math.cosh(2.0 * x) - 1.0) / 2.0;
    }

    public static double coshReduccionPotencia(double x) {
        return (Math.cosh(2.0 * x) + 1.0) / 2.0;
    }

    public static double tghReduccionPotencia(double x) {
        return (Math.cosh(2.0 * x) - 1.0) / (Math.cosh(2.0 * x) + 1.0);
    }

    // ==========================================
    //            LÍMITES ESPECIALES
    // ==========================================
    public static double limiteEulerExponencialUno() { return Math.E; }
    public static double limiteEulerExponencialDos() { return Math.E; }
    public static double limiteSenoXEntreX() { return 1.0; }
    public static double limiteCosenoMenosUnoEntreX() { return 0.0; }
    public static double limiteSinhXEntreX() { return 1.0; }
    public static double limiteXEntreLogaritmo() { return 1.0; }

    // ==========================================
    //    CONVERSIONES ANGULARES
    // ==========================================

    public static double gradosARadianes(double grados) {
        return grados * (Math.PI / 180.0);
    }

    public static double radianesAGrados(double radianes) {
        return radianes * (180.0 / Math.PI);
    }

    // ==========================================
    //   TRIÁNGULOS DE SUSTITUCIÓN TRIG.
    // ==========================================

    public static double[] trianguloCasoSeno(double u, double a) {
        if (Math.abs(u) > Math.abs(a)) throw new IllegalArgumentException("El valor de u no puede superar la hipotenusa a.");
        double catetoOpuesto = u;
        double hipotenusa = a;
        double catetoAdyacente = Math.sqrt(a * a - u * u);
        return new double[]{catetoOpuesto, catetoAdyacente, hipotenusa};
    }

    public static double[] trianguloCasoTangente(double u, double a) {
        double catetoOpuesto = u;
        double catetoAdyacente = a;
        double hipotenusa = Math.sqrt(a * a + u * u);
        return new double[]{catetoOpuesto, catetoAdyacente, hipotenusa};
    }

    public static double[] trianguloCasoSecante(double u, double a) {
        if (Math.abs(u) < Math.abs(a)) throw new IllegalArgumentException("La hipotenusa u debe ser mayor o igual al cateto a.");
        double hipotenusa = u;
        double catetoAdyacente = a;
        double catetoOpuesto = Math.sqrt(u * u - a * a);
        return new double[]{catetoOpuesto, catetoAdyacente, hipotenusa};
    }

    // ==========================================
    //     COORDENADAS CILÍNDRICAS
    // ==========================================

    public static double cilindricasARectangularesX(double r, double theta) {
        return r * Math.cos(theta);
    }

    public static double cilindricasARectangularesY(double r, double theta) {
        return r * Math.sin(theta);
    }

    public static double rectangularesACilindricasR(double x, double y) {
        return Math.sqrt(x * x + y * y);
    }

    public static double rectangularesACilindricasTheta(double x, double y) {
        return Math.atan2(y, x);
    }

    // ==========================================
    //     COORDENADAS ESFÉRICAS
    // ==========================================

    public static double esfericasARectangularesX(double rho, double theta, double phi) {
        return rho * Math.sin(phi) * Math.cos(theta);
    }

    public static double esfericasARectangularesY(double rho, double theta, double phi) {
        return rho * Math.sin(phi) * Math.sin(theta);
    }

    public static double esfericasARectangularesZ(double rho, double phi) {
        return rho * Math.cos(phi);
    }

    public static double rectangularesAEsfericasRho(double x, double y, double z) {
        return Math.sqrt(x * x + y * y + z * z);
    }

    public static double rectangularesAEsfericasPhi(double x, double y, double z) {
        double rho = rectangularesAEsfericasRho(x, y, z);
        if (rho == 0.0) return 0.0;
        return Math.acos(z / rho);
    }
}