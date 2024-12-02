package com.example.is_tfi.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class AgregarDiagnosticoDTO {
    @NotBlank(message = " El diagnostico no debe estar vacio.")
    private String nombre;
}
