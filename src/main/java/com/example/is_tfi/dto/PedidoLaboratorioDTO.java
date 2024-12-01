package com.example.is_tfi.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PedidoLaboratorioDTO {
    private Long id;
    private String texto;
    private MedicoDTO medico;
}
