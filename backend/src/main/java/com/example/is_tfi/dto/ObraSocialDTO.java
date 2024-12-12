package com.example.is_tfi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ObraSocialDTO {
    @Positive(message = "Ingrese un código valido.")
    private int codigo;

    @NotBlank(message = "Ingrese un nombre de obra social.")
    private String denominacion;

    @NotBlank(message = "Ingrese las siglas de la obre social.")
    private String sigla;
}
