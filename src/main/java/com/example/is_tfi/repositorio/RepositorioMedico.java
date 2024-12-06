package com.example.is_tfi.repositorio;

import com.example.is_tfi.dominio.Medico;

import java.util.Optional;

public interface RepositorioMedico {
    void guardarMedico(Medico medico);
    Optional<Medico> buscarMedicoPorMatricula(int matricula);
}
