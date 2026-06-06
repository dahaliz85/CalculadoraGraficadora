package com.biomath3d.math.parser;

import java.util.ArrayList;
import java.util.List;

public class Tokenizador {

    public static List<Token> tokenizar(String expresion, DetectorVariables detector) {
        List<Token> tokens = new ArrayList<>();
        int longitud = expresion.length();
        int i = 0;

        while (i < longitud) {
            char c = expresion.charAt(i);

            if (Character.isWhitespace(c)) {
                i++;
                continue;
            }

            if (Character.isDigit(c) || c == '.') {
                StringBuilder sb = new StringBuilder();
                while (i < longitud && (Character.isDigit(expresion.charAt(i)) || expresion.charAt(i) == '.')) {
                    sb.append(expresion.charAt(i));
                    i++;
                }
                tokens.add(new Token(TipoToken.NUMERO, sb.toString()));
                continue;
            }

            if (c == '(') {
                tokens.add(new Token(TipoToken.PARENTESIS_IZQ, "("));
                i++;
                continue;
            }
            if (c == ')') {
                tokens.add(new Token(TipoToken.PARENTESIS_DER, ")"));
                i++;
                continue;
            }
            if (c == '+' || c == '-' || c == '*' || c == '/' || c == '^') {
                tokens.add(new Token(TipoToken.OPERADOR, String.valueOf(c)));
                i++;
                continue;
            }

            if (Character.isLetter(c)) {
                StringBuilder sb = new StringBuilder();
                while (i < longitud && Character.isLetter(expresion.charAt(i))) {
                    sb.append(expresion.charAt(i));
                    i++;
                }
                String palabra = sb.toString();

                // 1. Validar funciones de tus catálogos
                if (palabra.equals("sin") || palabra.equals("cos") || palabra.equals("tan") ||
                        palabra.equals("ln")  || palabra.equals("log") || palabra.equals("sinh") ||
                        palabra.equals("cosh") || palabra.equals("tanh")) {
                    tokens.add(new Token(TipoToken.FUNCION, palabra));
                }
                // 2. Inferencia Dinámica de Variables
                else if (detector.getVariablesInferidas().contains(palabra)) {
                    tokens.add(new Token(TipoToken.VARIABLE, palabra));
                }
                // 3. Inferencia Dinámica de Constantes
                else if (detector.getConstantesInferidas().contains(palabra)) {
                    tokens.add(new Token(TipoToken.CONSTANTE, palabra));
                } else {
                    throw new IllegalArgumentException("Componente no identificado por el motor de inferencia: " + palabra);
                }
                continue;
            }

            throw new IllegalArgumentException("Carácter inválido detectado: " + c);
        }

        return tokens;
    }
}