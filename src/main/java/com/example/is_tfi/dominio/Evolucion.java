package com.example.is_tfi.dominio;

import java.time.LocalDateTime;

public class Evolucion {
    private String informe;
    private Medico medico;
    private LocalDateTime fechaHora;

    public Evolucion(String informe, Medico medico) {
        this.informe = informe;
        this.medico = medico;
        this.fechaHora = LocalDateTime.now();
    }
}
