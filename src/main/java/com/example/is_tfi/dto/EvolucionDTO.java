package com.example.is_tfi.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class EvolucionDTO {
    private Long id;
    private String informe;
    private MedicoDTO medico;
    private LocalDateTime fechaHora;
}
