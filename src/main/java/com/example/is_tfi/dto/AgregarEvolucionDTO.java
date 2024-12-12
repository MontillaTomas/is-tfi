package com.example.is_tfi.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AgregarEvolucionDTO {
    @NotBlank(message = " El informe no debe estar vacio.")
    private String informe;
}
