package com.example.is_tfi.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DiagnosticoDTO {
    private String nombre;
    private List<EvolucionDTO> evoluciones;
}
