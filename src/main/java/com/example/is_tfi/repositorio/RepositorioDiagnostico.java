package com.example.is_tfi.repositorio;

import com.example.is_tfi.dominio.Diagnostico;
import jdk.jshell.Diag;

import java.util.List;
import java.util.Optional;

public interface RepositorioDiagnostico {
    Optional<Diagnostico> buscarDiagnosticoPorNombre(String nombre);
}
