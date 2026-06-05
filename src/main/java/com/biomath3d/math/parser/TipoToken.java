package com.biomath3d.math.parser;

/**
 * Define los componentes lógicos que el analizador matemático puede reconocer.
 */
public enum TipoToken {
    NUMERO,         // Ej: 3.1416, 42, 0.5
    VARIABLE,       // Ej: x, y, z
    CONSTANTE,      // Ej: a (la constante de ajuste de tu pantalla)
    OPERADOR,       // Ej: +, -, *, /, ^
    FUNCION,        // Ej: sin, cos, tan, ln, log
    PARENTESIS_IZQ, // (
    PARENTESIS_DER  // )
}