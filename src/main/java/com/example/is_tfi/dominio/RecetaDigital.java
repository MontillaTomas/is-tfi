package com.example.is_tfi.dominio;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class RecetaDigital {
    private List<Medicamento> medicamentos;
    private LocalDateTime fechaHora;
    private Medico medico;

    public RecetaDigital(List<Medicamento> medicamentos,
                         Medico medico) {
        if(medicamentos.isEmpty() || medicamentos.size() > 2) {
            throw new IllegalArgumentException("La cantidad de medicamentos debe ser entre 1 y 2");
        }
        this.medicamentos = medicamentos;
        this.fechaHora = LocalDateTime.now();
        this.medico = medico;
    }

    public List<Medicamento> getMedicamentos() {
        return medicamentos;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public Medico getMedico() {
        return medico;
    }
    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }
}
