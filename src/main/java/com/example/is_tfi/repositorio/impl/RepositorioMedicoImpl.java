package com.example.is_tfi.repositorio.impl;

import com.example.is_tfi.dominio.Direccion;
import com.example.is_tfi.dominio.Medico;
import com.example.is_tfi.dominio.Usuario;
import com.example.is_tfi.repositorio.RepositorioMedico;
import com.example.is_tfi.repositorio.RepositorioUsuario;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.List;

public class RepositorioMedicoImpl implements RepositorioMedico {
    private List<Medico> medicos;
    private final RepositorioUsuario repositorioUsuario;

    public RepositorioMedicoImpl() {
        this.repositorioUsuario = new RepositorioUsuarioImpl();
        medicos = new ArrayList<>();

        Medico medico = new Medico(44747797L,
                20447477972L,
                "Dr House",
                LocalDate.of(2003, 4, 2),
                "doctor.house@gmail.com",
                "+123123123",
                new Direccion("Calle Falsa", 123, "1234", "Springfield"),
                123456,
                "Clinico");

        Usuario usuarioMedico = repositorioUsuario.buscarUsuarioPorEmail("email@email.com").get();
        medico.setUsuario(usuarioMedico);

        this.medicos.add(medico);
    }

    @Override
    public void guardarMedico(Medico medico) {
        this.medicos.add(medico);
    }

    @Override
    public Optional<Medico> buscarMedicoPorMatricula(int matricula) {
        return this.medicos.stream()
                .filter(m -> m.getMatricula() == matricula)
                .findFirst();
    }

    public Optional<Medico> buscarMedicoPorEmailDeUsuario(String email) {
        return this.medicos.stream()
                .filter(m -> m.getUsuario().getEmail().equals(email))
                .findFirst();
    }
}
