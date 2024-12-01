package com.example.is_tfi.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PacienteDTO {
    private Long dni;
    private Long cuil;
    private String nombre;
    private LocalDate fechaNacimiento;
    private String email;
    private String telefono;
    private DireccionDTO direccion;
    private ObraSocialDTO obraSocial;
    private int numeroAfiliadoObraSocial;
    private HistoriaClinicaDTO historiaClinica;
}
