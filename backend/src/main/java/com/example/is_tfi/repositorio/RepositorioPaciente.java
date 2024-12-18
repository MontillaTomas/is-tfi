package com.example.is_tfi.repositorio;

import com.example.is_tfi.dominio.Paciente;

import java.util.List;
import java.util.Optional;

public interface RepositorioPaciente {
    void guardarPaciente(Paciente paciente);
    Optional<Paciente> buscarPacientePorDni(Long dni);
    Optional<Paciente> buscarPacientePorCuil(Long cuil);
    List<Paciente> obtenerPacientes();
    List<Paciente> buscarPacientesPorTexto(String texto);
}
