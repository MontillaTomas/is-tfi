package com.example.is_tfi.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class RecetaDigitalDTO {
    private Long id;
    private List<MedicamentoDTO> medicamentos;
    private LocalDateTime fechaHora;
    private MedicoDTO medico;
}
