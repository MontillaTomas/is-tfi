package com.example.is_tfi.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CrearPacienteDTO {
    @NotNull(message = " Ingrese un DNI válido")
    @Min(value = 1000000L, message = "El dni debe tener al menos 7 dígitos.")
    @Max(value = 99999999L, message = "El dni no puede tener más de 8 digitos.")
    @Positive(message = "El DNI no puede ser negativo.")
    private Long dni;


    @NotNull(message = " Ingrese un CUIL válido")
    @Min(value = 1000000000L, message = "El CUIL debe tener al menos 10 dígitos.")
    @Max(value = 99999999999L, message = "El CUIL no puede tener más de 11 digitos.")
    @Positive(message = "El CUIL no puede ser negativo.")
    private Long cuil;

    @NotBlank(message = " El nombre del paciente no puede estar vacio.")
    private String nombre;


    @NotNull(message = " Ingrese una fecha de nacimiento.")
    @PastOrPresent
    private LocalDate fechaNacimiento;

    @NotBlank(message = " Mail no puede estar vacio.")
    private String email;

    @NotBlank(message = " Telefono no puede estar vacio.")
    private String telefono;

    @Valid
    private DireccionDTO direccion;

    @Valid
    private ObraSocialDTO obraSocial;

    @NotNull(message = " Ingrese un numero de afiliado válido")
    @Positive(message = "El numero de afiliado no puede ser negativo.")
    private int numeroAfiliadoObraSocial;
}
