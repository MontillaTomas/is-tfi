package com.example.is_tfi.repositorio.impl;

import com.example.is_tfi.dominio.*;
import com.example.is_tfi.repositorio.RepositorioPaciente;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class RepositorioPacienteImpl implements RepositorioPaciente {
    private List<Paciente> pacientes;

    public RepositorioPacienteImpl() {
        pacientes = new ArrayList<>();

        Medico medico = new Medico(44747797L,
                20447477972L,
                "Dr House",
                LocalDate.of(2003, 4, 2),
                "doctor.house@gmail.com",
                "+123123123",
                new Direccion("Calle Falsa", 123, "1234", "Springfield"),
                123456,
                "Clinico");

        Paciente paciente = new Paciente(44747797L,
                20447477972L,
                "Tomas Montilla",
                LocalDate.of(2003, 4, 2),
                "montilla.tom1@gmail.com",
                "+123123123",
                new ObraSocial(1, "OBRA SOCIAL DEL PERSONAL ADMINISTRATIVO Y TECNICO DE LA CONSTRUCCION Y AFINES", "OSPATCA"),
                123456,
                new Direccion("Calle Falsa", 123, "1234", "Springfield"));

        paciente.agregarDiagnostico("Gripe"); // Este diagnostico tendra 2 evoluciones
        paciente.agregarDiagnostico("Dengue"); // Este diagnostico tendra 1 evolucion
        paciente.agregarDiagnostico("Zika"); // Este diagnostico no tendra evoluciones

        paciente.agregarEvolucion("Gripe", "El paciente se encuentra estable", medico);
        paciente.agregarEvolucion("Gripe", "El paciente se encuentra mejor", medico);
        paciente.agregarEvolucion("Dengue", "El paciente se encuentra peor", medico);

        paciente.crearRecetaDigital("Gripe", 1L, List.of(new Medicamento(1, "Ibuprofeno", "A"), new Medicamento(2, "Tafirol", "B")), medico);
        paciente.crearRecetaDigital("Dengue", 1L, List.of(new Medicamento(1, "Ibuprofeno", "A")), medico);

        paciente.crearPedidoLaboratorio("Gripe", 1L, "Hacer analisis de sangre", medico);
        paciente.crearPedidoLaboratorio("Dengue", 1L, "Hacer analisis de orina", medico);

        this.pacientes.add(paciente);
    }

    @Override
    public void guardarPaciente(Paciente paciente) {
        this.pacientes.add(paciente);
    }

    @Override
    public Optional<Paciente> buscarPacientePorDni(Long dni) {
        return this.pacientes.stream()
                .filter(paciente -> Objects.equals(paciente.getDni(), dni))
                .findFirst();
    }

    @Override
    public Optional<Paciente> buscarPacientePorCuil(Long cuil) {
        return this.pacientes.stream()
                .filter(paciente -> Objects.equals(paciente.getCuil(), cuil))
                .findFirst();
    }

    @Override
    public List<Paciente> obtenerPacientes() {
        return this.pacientes;
    }
}
