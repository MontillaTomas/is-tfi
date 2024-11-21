package com.example.is_tfi.repositorio.impl;

import com.example.is_tfi.dominio.Medico;
import com.example.is_tfi.dominio.Paciente;
import com.example.is_tfi.repositorio.RepositorioPaciente;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RepositorioPacienteImpl implements RepositorioPaciente {
    private List<Paciente> pacientes;

    public RepositorioPacienteImpl() {
        pacientes = new ArrayList<>();

        Medico medico = new Medico("Dr House", 12345);
        Paciente paciente = new Paciente(44747797, "Juan Perez", LocalDate.of(1990, 1, 1));
        paciente.agregarDiagnostico("Gripe"); // Este diagnostico tendra 2 evoluciones
        paciente.agregarDiagnostico("Dengue"); // Este diagnostico tendra 1 evolucion
        paciente.agregarDiagnostico("Zika"); // Este diagnostico no tendra evoluciones
        paciente.agregarEvolucion("Gripe", "El paciente se encuentra estable", medico);
        paciente.agregarEvolucion("Gripe", "El paciente se encuentra mejor", medico);
        paciente.agregarEvolucion("Dengue", "El paciente se encuentra peor", medico);
        this.pacientes.add(paciente);
    }

    @Override
    public void guardarPaciente(Paciente paciente) {
        this.pacientes.add(paciente);
    }

    @Override
    public Optional<Paciente> buscarPacientePorDni(int dni) {
        return this.pacientes.stream()
                .filter(paciente -> paciente.getDni() == dni)
                .findFirst();
    }
}
