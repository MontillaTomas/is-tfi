package com.example.is_tfi.excepciones;

public class PacienteNoEncontradoExcepcion extends RuntimeException {
    public PacienteNoEncontradoExcepcion(String message) {
        super(message);
    }
}
