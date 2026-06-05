package com.biomath3d.math.parser;

/**
 * Representa una unidad léxica mínima dentro de una expresión matemática.
 */
public class Token {
    private final TipoToken tipo;
    private final String valor;

    public Token(TipoToken tipo, String valor) {
        this.tipo = tipo;
        this.valor = valor;
    }

    public TipoToken getTipo() {
        return tipo;
    }

    public String getValor() {
        return valor;
    }

    @Override
    public String toString() {
        return String.format("[%s: %s]", tipo, valor);
    }
}