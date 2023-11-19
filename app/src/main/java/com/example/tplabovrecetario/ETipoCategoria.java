package com.example.tplabovrecetario;

public enum ETipoCategoria {
    American,
    Mexican,
    Italian;

    public static ETipoCategoria fromString(String texto) {
        for (ETipoCategoria b : ETipoCategoria.values()) {
            if (b.name().equalsIgnoreCase(texto)) {
                return b;
            }
        }
        return null;
    }
}
