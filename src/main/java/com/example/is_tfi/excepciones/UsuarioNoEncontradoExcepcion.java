package com.example.is_tfi.excepciones;

public class UsuarioNoEncontradoExcepcion extends RuntimeException {
    public UsuarioNoEncontradoExcepcion(String message) {
        super(message);
    }
}