package com.biomath3d.math.parser;

import java.util.ArrayList;
import java.util.List;

/**
 * Se encarga de romper una cadena de texto matemática en una lista de Tokens legibles.
 */
public class Tokenizador {

    public static List<Token> tokenizar(String expresion) {
        List<Token> tokens = new ArrayList<>();
        int longitud = expresion.length();
        int i = 0;

        while (i < longitud) {
            char c = expresion.charAt(i);

            // 1. Ignorar espacios en blanco
            if (Character.isWhitespace(c)) {
                i++;
                continue;
            }

            // 2. Reconocer números (incluyendo decimales)
            if (Character.isDigit(c) || c == '.') {
                StringBuilder sb = new StringBuilder();
                while (i < longitud && (Character.isDigit(expresion.charAt(i)) || expresion.charAt(i) == '.')) {
                    sb.append(expresion.charAt(i));
                    i++;
                }
                tokens.add(new Token(TipoToken.NUMERO, sb.toString()));
                continue;
            }

            // 3. Reconocer paréntesis y operadores directos
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

            // 4. Reconocer palabras (Variables x, y, z, constantes o funciones como sin, cos, ln)
            if (Character.isLetter(c)) {
                StringBuilder sb = new StringBuilder();
                while (i < longitud && Character.isLetter(expresion.charAt(i))) {
                    sb.append(expresion.charAt(i));
                    i++;
                }
                String palabra = sb.toString();

                // Validamos si la palabra es una función trigonométrica/logarítmica conocida
                if (palabra.equals("sin") || palabra.equals("cos") || palabra.equals("tan") ||
                        palabra.equals("ln")  || palabra.equals("log") || palabra.equals("sinh") ||
                        palabra.equals("cosh") || palabra.equals("tanh")) {
                    tokens.add(new Token(TipoToken.FUNCION, palabra));
                } else if (palabra.equals("x") || palabra.equals("y") || palabra.equals("z")) {
                    tokens.add(new Token(TipoToken.VARIABLE, palabra));
                } else if (palabra.equals("a")) {
                    tokens.add(new Token(TipoToken.CONSTANTE, palabra));
                } else {
                    throw new IllegalArgumentException("Componente desconocido en la ecuación: " + palabra);
                }
                continue;
            }

            // Si encuentra un carácter extraño (ej: $, #, @) truena de forma segura
            throw new IllegalArgumentException("Carácter inválido detectado: " + c);
        }

        return tokens;
    }
}