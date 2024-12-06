package com.example.is_tfi.repositorio.impl;

import com.example.is_tfi.dominio.Usuario;
import com.example.is_tfi.repositorio.RepositorioUsuario;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class RepositorioUsuarioImpl implements RepositorioUsuario {
    private List<Usuario> usuarios;

    public RepositorioUsuarioImpl() {
        this.usuarios = new ArrayList<>();

        // La contraseña es "12345"
        Usuario usuario = new Usuario("email@email.com", "$2a$10$pb8Ks6sMbuL59YyiEgu7MORYpsAcQ4ew83GE7huqq/4MPqYLuA2fG");

        this.usuarios.add(usuario);
    }

    @Override
    public void guardarUsuario(Usuario usuario) {
        this.usuarios.add(usuario);
    }

    @Override
    public Optional<Usuario> buscarUsuarioPorEmail(String email) {
        return usuarios.stream().filter(usuario -> usuario.getEmail().equals(email)).findFirst();
    }
}
