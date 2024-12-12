package com.example.is_tfi.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class HistoriaClinicaDTO {
    private List<DiagnosticoDTO> diagnosticos;
}
