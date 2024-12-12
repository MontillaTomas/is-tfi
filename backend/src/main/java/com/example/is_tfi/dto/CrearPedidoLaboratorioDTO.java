package com.example.is_tfi.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CrearPedidoLaboratorioDTO {
    @NotBlank(message = "Pedido de laboratorio no puede estar vacio.")
    private String texto;
}
