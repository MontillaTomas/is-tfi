package com.example.is_tfi.repositorio.impl;

import com.example.is_tfi.dominio.Diagnostico;
import com.example.is_tfi.dominio.Paciente;
import com.example.is_tfi.repositorio.RepositorioDiagnostico;
import org.springframework.stereotype.Repository;

import java.text.Normalizer;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Repository
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

    private String eliminarDiacriticos(String nombre) {
        if (nombre == null) return null;
        String textoNormalizado = Normalizer.normalize(nombre, Normalizer.Form.NFD);
        Pattern patronDiacriticos = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return patronDiacriticos.matcher(textoNormalizado).replaceAll("");
    }

    @Override
    public List<Diagnostico> obtenerDiagnostico() {
        return this.diagnosticos;
    }

    @Override
    public Optional<Diagnostico> buscarDiagnosticoPorNombre(String nombre) {
        String textoNormalizado = eliminarDiacriticos(nombre.toUpperCase());
        return this.diagnosticos.stream()
                .filter(p -> {
                    String nombreNormalizado = eliminarDiacriticos(p.getNombre().toUpperCase());
                    return nombreNormalizado.contains(textoNormalizado);
                })
                .findFirst(); // Encuentra el primer resultado coincidente
    }
}
