package com.example.is_tfi.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AutenticacionPeticionDTO {
    @NotBlank(message = "El email no puede estar vacío")
    private String email;
    @NotBlank(message = "La contraseña no puede estar vacía")
    private String contrasena;
}
