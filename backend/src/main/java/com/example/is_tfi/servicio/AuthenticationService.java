package com.example.is_tfi.servicio;

import com.example.is_tfi.dominio.Medico;
import com.example.is_tfi.dominio.Usuario;
import com.example.is_tfi.dto.AutenticacionPeticionDTO;
import com.example.is_tfi.repositorio.RepositorioUsuario;
import com.example.is_tfi.repositorio.impl.RepositorioMedicoImpl;
import com.example.is_tfi.repositorio.impl.RepositorioUsuarioImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private final RepositorioUsuarioImpl repositorioUsuario;
    private final RepositorioMedicoImpl repositorioMedico;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(
        RepositorioUsuario repositorioUsuario,
        AuthenticationManager authenticationManager,
        PasswordEncoder passwordEncoder
    ) {
        this.repositorioUsuario = (RepositorioUsuarioImpl) repositorioUsuario;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.repositorioMedico = new RepositorioMedicoImpl();
    }

    public Medico authenticate(AutenticacionPeticionDTO dto) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                dto.getEmail(),
                dto.getContrasena()
            )
        );

        return repositorioMedico.buscarMedicoPorEmailDeUsuario(dto.getEmail()).orElseThrow();
    }
}
