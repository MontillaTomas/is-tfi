package com.example.is_tfi.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class MedicoDTO {
    private Long dni;
    private Long cuil;
    private String nombre;
    private LocalDate fechaNacimiento;
    private String email;
    private String telefono;
    private DireccionDTO direccion;
    private int matricula;
    private String especialidad;
}