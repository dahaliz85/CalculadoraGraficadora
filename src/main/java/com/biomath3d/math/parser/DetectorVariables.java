package com.biomath3d.math.parser;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Analiza la estructura de la declaración de la función para inferir de forma autónoma
 * cuáles literales actúan como variables independientes y cuáles como constantes.
 */
public class DetectorVariables {

    private String expresionLimpia;
    private final List<String> variablesInferidas = new ArrayList<>();
    private final List<String> constantesInferidas = new ArrayList<>();

    public void analizarPropuesta(String cadenaCompleta) {
        variablesInferidas.clear();
        constantesInferidas.clear();

        String ladoDerecho = cadenaCompleta;

        // 1. Si contiene un signo '=', separamos la izquierda de la derecha
        if (cadenaCompleta.contains("=")) {
            int indiceIgual = cadenaCompleta.indexOf('=');
            String ladoIzquierdo = cadenaCompleta.substring(0, indiceIgual).trim();
            ladoDerecho = cadenaCompleta.substring(indiceIgual + 1).trim();

            // Buscamos si tiene formato funcional tipo f(x,y) o z(a,b)
            Pattern p = Pattern.compile("\\b[a-zA-Z]\\s*\\(([^)]+)\\)");
            Matcher m = p.matcher(ladoIzquierdo);
            if (m.find()) {
                String argumentos = m.group(1); // Extrae lo que está dentro: "x,y" o "a,b"
                for (String arg : argumentos.split(",")) {
                    String var = arg.trim();
                    if (!var.isEmpty() && Character.isLetter(var.charAt(0))) {
                        variablesInferidas.add(var);
                    }
                }
            } else {
                // Si es tipo 'z = ...' o 'y = ...', la letra de la izquierda no es variable independiente
                // Buscaremos las variables directamente en el lado derecho.
            }
        }

        this.expresionLimpia = ladoDerecho;

        // 2. Escaneo del lado derecho para encontrar letras sueltas (variables o constantes)
        // Usamos LinkedHashSet para mantener el orden de aparición sin duplicar letras
        Set<String> letrasEncontradas = new LinkedHashSet<>();
        Pattern pLetras = Pattern.compile("\\b[a-zA-Z]\\b");
        Matcher mLetras = pLetras.matcher(ladoDerecho);

        while (mLetras.find()) {
            String letra = mLetras.group();
            // Ignoramos nombres de funciones conocidas para que no se confundan con constantes
            if (!esFuncionReservada(letra)) {
                letrasEncontradas.add(letra);
            }
        }

        // 3. Si no se declararon variables explícitas a la izquierda, inferimos por orden de aparición
        if (variablesInferidas.isEmpty()) {
            int contador = 0;
            for (String letra : letrasEncontradas) {
                if (contador < 2) { // Las primeras 2 letras distintas se asumen como variables (x, y) o (a, b)
                    variablesInferidas.add(letra);
                    contador++;
                } else { // El resto de letras se vuelven constantes automáticas (c, k, m, etc.)
                    constantesInferidas.add(letra);
                }
            }
        } else {
            // Si ya teníamos variables declaradas a la izquierda, cualquier otra letra a la derecha es constante
            for (String letra : letrasEncontradas) {
                if (!variablesInferidas.contains(letra)) {
                    constantesInferidas.add(letra);
                }
            }
        }
    }

    private boolean esFuncionReservada(String palabra) {
        return palabra.equals("sin") || palabra.equals("cos") || palabra.equals("tan") ||
                palabra.equals("ln")  || palabra.equals("log") || palabra.equals("sinh") ||
                palabra.equals("cosh") || palabra.equals("tanh");
    }

    public String getExpresionLimpia() { return expresionLimpia; }
    public List<String> getVariablesInferidas() { return variablesInferidas; }
    public List<String> getConstantesInferidas() { return constantesInferidas; }
}