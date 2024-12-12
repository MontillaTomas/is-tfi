package com.example.is_tfi.excepciones;

public class MedicoNoEncontradoExcepcion extends RuntimeException {
    public MedicoNoEncontradoExcepcion(String message) {
        super(message);
    }
}
