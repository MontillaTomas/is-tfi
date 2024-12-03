package com.example.is_tfi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MedicamentoDTO {
    @Positive(message = "El codigo no puede ser 0 o negativo")
    private int codigo;
    @NotBlank(message = "La descripcion no puede estar en blanco.")
    private String descripcion;
    @NotBlank(message = "El formato no puede estar en blanco.")
    private String formato;
}
