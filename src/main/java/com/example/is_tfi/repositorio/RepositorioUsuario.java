package com.example.is_tfi.repositorio;

import com.example.is_tfi.dominio.Usuario;

import java.util.Optional;

public interface RepositorioUsuario {
    void guardarUsuario(Usuario usuario);
    Optional<Usuario> buscarUsuarioPorEmail(String email);
}
