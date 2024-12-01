package com.example.is_tfi.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DireccionDTO {
    private String calle;
    private int numero;
    private int piso;
    private String departamento;
    private String codigoPostal;
    private String ciudad;
}
