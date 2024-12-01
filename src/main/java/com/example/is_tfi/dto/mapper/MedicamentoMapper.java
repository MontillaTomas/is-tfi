package com.example.is_tfi.dto.mapper;

import com.example.is_tfi.dominio.Medicamento;
import com.example.is_tfi.dto.MedicamentoDTO;

import java.util.ArrayList;
import java.util.List;

public class MedicamentoMapper implements EntidadMapper<Medicamento, MedicamentoDTO> {
    @Override
    public Medicamento toEntity(MedicamentoDTO dto) {
        if (dto == null) return null;

        return new Medicamento(dto.getCodigo(), dto.getDescripcion(), dto.getFormato());
    }

    public List<Medicamento> toEntity(List<MedicamentoDTO> dtos) {
        if (dtos == null) return null;

        List<Medicamento> entidades = new ArrayList<>();
        for (MedicamentoDTO dto : dtos) {
            entidades.add(toEntity(dto));
        }

        return entidades;
    }

    @Override
    public MedicamentoDTO toDto(Medicamento entidad) {
        if (entidad == null) return null;

        MedicamentoDTO dto = new MedicamentoDTO();
        dto.setCodigo(entidad.getCodigo());
        dto.setDescripcion(entidad.getDescripcion());
        dto.setFormato(entidad.getFormato());

        return dto;
    }

    public List<MedicamentoDTO> toDto(List<Medicamento> entidades) {
        if (entidades == null) return null;

        List<MedicamentoDTO> dtos = new ArrayList<>();
        for (Medicamento entidad : entidades) {
            dtos.add(toDto(entidad));
        }

        return dtos;
    }
}
