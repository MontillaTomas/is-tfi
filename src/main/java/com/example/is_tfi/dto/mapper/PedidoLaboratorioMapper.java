package com.example.is_tfi.dto.mapper;

import com.example.is_tfi.dominio.PedidoLaboratorio;
import com.example.is_tfi.dto.PedidoLaboratorioDTO;

public class PedidoLaboratorioMapper {
    private final MedicoMapper medicoMapper;

    public PedidoLaboratorioMapper() {
        this.medicoMapper = new MedicoMapper();
    }

    public PedidoLaboratorio toEntity(PedidoLaboratorioDTO dto) {
        if (dto == null) return null;

        return new PedidoLaboratorio(dto.getTexto(), medicoMapper.toEntity(dto.getMedico()));
    }

    public PedidoLaboratorioDTO toDtoWithId(PedidoLaboratorio entidad, Long id) {
        if (entidad == null) return null;

        PedidoLaboratorioDTO dto = new PedidoLaboratorioDTO();
        dto.setId(id);
        dto.setTexto(entidad.getTexto());
        dto.setMedico(medicoMapper.toDto(entidad.getMedico()));

        return dto;
    }
}
