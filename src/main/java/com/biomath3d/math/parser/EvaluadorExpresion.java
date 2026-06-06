package com.biomath3d.math.parser;

import com.biomath3d.math.form.FormularioAlgebraLogaritmos;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Implementa el algoritmo Shunting-yard de Dijkstra para convertir expresiones
 * a Notación Polaca Inversa y evaluarlas numéricamente en el espacio 3D.
 */
public class EvaluadorExpresion {

    /**
     * Convierte una lista de tokens infijos a notación postfija (Polaca Inversa).
     * Corrige el flujo de precedencia para funciones compuestas continuas.
     */
    public static List<Token> convertirAPostfijo(List<Token> tokensInfijos) {
        List<Token> salida = new ArrayList<>();
        Stack<Token> pilaOperadores = new Stack<>();

        for (Token token : tokensInfijos) {
            switch (token.getTipo()) {
                case NUMERO:
                case VARIABLE:
                case CONSTANTE:
                    salida.add(token);
                    break;

                case FUNCION:
                case PARENTESIS_IZQ:
                    pilaOperadores.push(token);
                    break;

                case OPERADOR:
                    while (!pilaOperadores.isEmpty() &&
                            pilaOperadores.peek().getTipo() != TipoToken.PARENTESIS_IZQ &&
                            obtenerPrecedencia(pilaOperadores.peek().getValor()) >= obtenerPrecedencia(token.getValor())) {
                        salida.add(pilaOperadores.pop());
                    }
                    pilaOperadores.push(token);
                    break;

                case PARENTESIS_DER:
                    while (!pilaOperadores.isEmpty() && pilaOperadores.peek().getTipo() != TipoToken.PARENTESIS_IZQ) {
                        salida.add(pilaOperadores.pop());
                    }
                    if (pilaOperadores.isEmpty()) {
                        throw new IllegalArgumentException("Error de sintaxis: Paréntesis desbalanceados.");
                    }
                    pilaOperadores.pop(); // Quitar el paréntesis izquierdo

                    // Si arriba de la pila hay una función (ej: sin), se va a la salida
                    if (!pilaOperadores.isEmpty() && pilaOperadores.peek().getTipo() == TipoToken.FUNCION) {
                        salida.add(pilaOperadores.pop());
                    }
                    break;
            }
        }

        while (!pilaOperadores.isEmpty()) {
            Token superior = pilaOperadores.pop();
            if (superior.getTipo() == TipoToken.PARENTESIS_IZQ) {
                throw new IllegalArgumentException("Error de sintaxis: Paréntesis desbalanceados.");
            }
            salida.add(superior);
        }

        return salida;
    }

    /**
     * Evalúa numéricamente una expresión en notación postfija en un punto determinado.
     * Versión robusta que garantiza la consistencia de la malla y el reporte.
     */
    public static double evaluarPostfijo(List<Token> tokensPostfijos, double x, double y, double z, double constanteA, DetectorVariables detector) {
        Stack<Double> pilaNumeros = new Stack<>();
        List<String> listaVars = (detector != null) ? detector.getVariablesInferidas() : new ArrayList<>();

        for (Token token : tokensPostfijos) {
            if (token.getTipo() == TipoToken.NUMERO) {
                pilaNumeros.push(Double.parseDouble(token.getValor()));
            }
            else if (token.getTipo() == TipoToken.VARIABLE) {
                String nombreLetra = token.getValor().toLowerCase().trim();

                if (detector != null && !listaVars.isEmpty() && nombreLetra.equals(listaVars.get(0).toLowerCase().trim())) {
                    pilaNumeros.push(x);
                } else if (detector != null && listaVars.size() > 1 && nombreLetra.equals(listaVars.get(1).toLowerCase().trim())) {
                    pilaNumeros.push(y);
                } else if (detector != null && listaVars.size() > 2 && nombreLetra.equals(listaVars.get(2).toLowerCase().trim())) {
                    pilaNumeros.push(z);
                } else {
                    if (nombreLetra.equals("x")) pilaNumeros.push(x);
                    else if (nombreLetra.equals("y")) pilaNumeros.push(y);
                    else if (nombreLetra.equals("z")) pilaNumeros.push(z);
                    else pilaNumeros.push(x);
                }
            }
            else if (token.getTipo() == TipoToken.CONSTANTE) {
                pilaNumeros.push(constanteA);
            }
            else if (token.getTipo() == TipoToken.OPERADOR) {
                // Evitamos el 'continue' extrayendo con validación controlada para no romper la pila
                double b = (!pilaNumeros.isEmpty()) ? pilaNumeros.pop() : 0.0;
                double a = (!pilaNumeros.isEmpty()) ? pilaNumeros.pop() : 0.0;
                pilaNumeros.push(ejecutarOperacionBinaria(a, b, token.getValor()));
            }
            else if (token.getTipo() == TipoToken.FUNCION) {
                double argumento = (!pilaNumeros.isEmpty()) ? pilaNumeros.pop() : 0.0;
                pilaNumeros.push(ejecutarFuncion(argumento, token.getValor()));
            }
        }

        // Retorno seguro y garantizado
        if (!pilaNumeros.isEmpty()) {
            return pilaNumeros.pop();
        }
        return 0.0;
    }

    /**
     * Versión sobrecargada de 5 argumentos para el renderizado nativo de la malla 3D.
     * No requiere pasar un DetectorVariables.
     */
    public static double evaluarPostfijo(List<Token> tokensPostfijos, double x, double y, double z, double constanteA) {
        Stack<Double> pilaNumeros = new Stack<>();

        for (Token token : tokensPostfijos) {
            if (token.getTipo() == TipoToken.NUMERO) {
                pilaNumeros.push(Double.parseDouble(token.getValor()));
            }
            else if (token.getTipo() == TipoToken.VARIABLE) {
                String nombreLetra = token.getValor().toLowerCase().trim();

                // Mapeo directo y seguro de variables en la cuadrícula
                if (nombreLetra.equals("x")) pilaNumeros.push(x);
                else if (nombreLetra.equals("y")) pilaNumeros.push(y);
                else if (nombreLetra.equals("z")) pilaNumeros.push(z);
                else pilaNumeros.push(x); // Resguardo
            }
            else if (token.getTipo() == TipoToken.CONSTANTE) {
                pilaNumeros.push(constanteA);
            }
            else if (token.getTipo() == TipoToken.OPERADOR) {
                double b = (!pilaNumeros.isEmpty()) ? pilaNumeros.pop() : 0.0;
                double a = (!pilaNumeros.isEmpty()) ? pilaNumeros.pop() : 0.0;
                pilaNumeros.push(ejecutarOperacionBinaria(a, b, token.getValor()));
            }
            else if (token.getTipo() == TipoToken.FUNCION) {
                double argumento = (!pilaNumeros.isEmpty()) ? pilaNumeros.pop() : 0.0;
                pilaNumeros.push(ejecutarFuncion(argumento, token.getValor()));
            }
        }

        if (!pilaNumeros.isEmpty()) {
            return pilaNumeros.pop();
        }
        return 0.0;
    }



    private static int obtenerPrecedencia(String operador) {
        if (operador.equals("+") || operador.equals("-")) return 1;
        if (operador.equals("*") || operador.equals("/")) return 2;
        if (operador.equals("^")) return 3;
        return 0;
    }

    private static double ejecutarOperacionBinaria(double a, double b, String operador) {
        switch (operador) {
            case "+": return a + b;
            case "-": return a - b;
            case "*": return a * b;
            case "/":
                if (b == 0.0) throw new ArithmeticException("División entre cero detectada en la superficie.");
                return a / b;
            case "^": return Math.pow(a, b);
            default: throw new IllegalArgumentException("Operador desconocido: " + operador);
        }
    }

    private static double ejecutarFuncion(double arg, String funcion) {
        switch (funcion) {
            case "sin": return Math.sin(arg);
            case "cos": return Math.cos(arg);
            case "tan": return Math.tan(arg);
            case "sinh": return Math.sinh(arg);
            case "cosh": return Math.cosh(arg);
            case "tanh": return Math.tanh(arg);
            case "ln": return FormularioAlgebraLogaritmos.definicionLogaritmo(Math.E, arg);
            case "log": return FormularioAlgebraLogaritmos.definicionLogaritmo(10.0, arg);
            default: throw new IllegalArgumentException("Función no soportada: " + funcion);
        }
    }

    /**
     * Genera la bitácora paso a paso utilizando la misma lógica segura del evaluador.
     */
    public static String obtenerDesarrolloPasoAPaso(List<Token> tokensPostfijos, double x, double y, double z, double constanteA, DetectorVariables detector) {
        StringBuilder bitacora = new StringBuilder();
        Stack<Double> pilaNumeros = new Stack<>();
        List<String> listaVars = (detector != null) ? detector.getVariablesInferidas() : new ArrayList<>();

        bitacora.append("====================================================\n");
        bitacora.append("         DESARROLLO PASO A PASO - BIOMATH 3D        \n");
        bitacora.append("====================================================\n");
        bitacora.append(String.format("Evaluando en el punto: P(x = %.2f, y = %.2f, z = %.2f) | Parámetro A = %.2f\n\n", x, y, z, constanteA));

        bitacora.append("1. Orden de evaluación en Notación Polaca Inversa:\n   ");
        for (Token t : tokensPostfijos) {
            bitacora.append(t.getValor()).append(" ");
        }
        bitacora.append("\n\n2. Ejecución secuencial en memoria (Uso de Pilas):\n");
        bitacora.append("----------------------------------------------------\n");

        int paso = 1;
        for (Token token : tokensPostfijos) {
            if (token.getTipo() == TipoToken.NUMERO) {
                double num = Double.parseDouble(token.getValor());
                pilaNumeros.push(num);
                bitacora.append(String.format("Paso %02d: Detecta número. Insertar en pila -> %.4f\n", paso++, num));
            }
            else if (token.getTipo() == TipoToken.VARIABLE) {
                String nombreLetra = token.getValor().toLowerCase().trim();
                double valVar = 0.0;

                if (detector != null && !listaVars.isEmpty() && nombreLetra.equals(listaVars.get(0).toLowerCase().trim())) {
                    valVar = x;
                } else if (detector != null && listaVars.size() > 1 && nombreLetra.equals(listaVars.get(1).toLowerCase().trim())) {
                    valVar = y;
                } else if (detector != null && listaVars.size() > 2 && nombreLetra.equals(listaVars.get(2).toLowerCase().trim())) {
                    valVar = z;
                } else {
                    if (nombreLetra.equals("x")) valVar = x;
                    else if (nombreLetra.equals("y")) valVar = y;
                    else if (nombreLetra.equals("z")) valVar = z;
                }

                pilaNumeros.push(valVar);
                bitacora.append(String.format("Paso %02d: Sustituye variable '%s' -> Insertar %.4f\n", paso++, token.getValor(), valVar));
            }
            else if (token.getTipo() == TipoToken.CONSTANTE) {
                pilaNumeros.push(constanteA);
                bitacora.append(String.format("Paso %02d: Sustituye constante 'a' -> Insertar %.4f\n", paso++, constanteA));
            }
            else if (token.getTipo() == TipoToken.OPERADOR) {
                double b = (!pilaNumeros.isEmpty()) ? pilaNumeros.pop() : 0.0;
                double a = (!pilaNumeros.isEmpty()) ? pilaNumeros.pop() : 0.0;
                double resOp = ejecutarOperacionBinaria(a, b, token.getValor());
                pilaNumeros.push(resOp);

                bitacora.append(String.format("Paso %02d: Operador [%s] -> Resuelve: %.4f %s %.4f = %.4f\n",
                        paso++, token.getValor(), a, token.getValor(), b, resOp));
            }
            else if (token.getTipo() == TipoToken.FUNCION) {
                double argumento = (!pilaNumeros.isEmpty()) ? pilaNumeros.pop() : 0.0;
                double resFunc = ejecutarFuncion(argumento, token.getValor());
                pilaNumeros.push(resFunc);

                bitacora.append(String.format("Paso %02d: Función [%s] -> Evalúa %s(%.4f) = %.4f\n",
                        paso++, token.getValor(), token.getValor(), argumento, resFunc));
            }

            bitacora.append("        Estado actual de la Pila: ").append(pilaNumeros.toString()).append("\n\n");
        }

        bitacora.append("----------------------------------------------------\n");
        double resultadoImpresion = pilaNumeros.isEmpty() ? 0.0 : pilaNumeros.peek();
        bitacora.append(String.format("RESULTADO FINAL: z = %.6f\n", resultadoImpresion));
        bitacora.append("====================================================\n");

        return bitacora.toString();
    }
}