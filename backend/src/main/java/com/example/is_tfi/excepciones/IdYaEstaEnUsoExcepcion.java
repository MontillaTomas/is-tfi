package com.example.is_tfi.excepciones;

public class IdYaEstaEnUsoExcepcion extends RuntimeException {
    public IdYaEstaEnUsoExcepcion(String s) {
        super(s);
    }
}
