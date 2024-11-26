package com.example.is_tfi.repositorio.impl;

import com.example.is_tfi.dominio.Diagnostico;
import com.example.is_tfi.repositorio.RepositorioDiagnostico;

import java.util.List;
import java.util.Optional;

public class RepositorioDiagnosticoImpl implements RepositorioDiagnostico {
    private List<Diagnostico> diagnosticos;
    public RepositorioDiagnosticoImpl() {
        this.diagnosticos = List.of(
                new Diagnostico("Diabetes"),
                new Diagnostico("Hipertensión"),
                new Diagnostico("Covid-19"),
                new Diagnostico("Gripe"),
                new Diagnostico("Resfrío"),
                new Diagnostico("Dengue"),
                new Diagnostico("Zika"),
                new Diagnostico("Chikungunya")
        );
    }
    @Override
    public Optional<Diagnostico> buscarDiagnosticoPorNombre(String nombre) {
        return this.diagnosticos.stream()
                .filter(diagnostico -> diagnostico.getNombre().equals(nombre))
                .findFirst();
    }
}
