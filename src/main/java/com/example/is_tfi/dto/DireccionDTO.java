package com.example.is_tfi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DireccionDTO {
    @NotBlank(message = " Debe ingresar una calle.")
    private String calle;

    @Positive(message = " El numero no puede ser negativo.")
    private int numero;

    private int piso;

    @NotBlank(message = " Debe ingresar un departamento.")
    private String departamento;

    @NotBlank(message = " Debe ingresar un código postal.")
    private String codigoPostal;

    @NotBlank(message = "Debe ingresar una ciudad.")
    private String ciudad;
}
