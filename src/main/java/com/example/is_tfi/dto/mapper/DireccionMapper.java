package com.example.is_tfi.dto.mapper;

import com.example.is_tfi.dominio.Direccion;
import com.example.is_tfi.dto.DireccionDTO;

public class DireccionMapper implements EntidadMapper<Direccion, DireccionDTO> {
    @Override
    public Direccion toEntity(DireccionDTO dto) {
        if (dto == null) return null;

        return new Direccion(dto.getCalle(),
                dto.getNumero(),
                dto.getPiso(),
                dto.getDepartamento(),
                dto.getCodigoPostal(),
                dto.getCiudad());
    }

    @Override
    public DireccionDTO toDto(Direccion entidad) {
        if (entidad == null) return null;

        DireccionDTO dto = new DireccionDTO();
        dto.setCalle(entidad.getCalle());
        dto.setNumero(entidad.getNumero());
        dto.setPiso(entidad.getPiso());
        dto.setDepartamento(entidad.getDepartamento());
        dto.setCodigoPostal(entidad.getCodigoPostal());
        dto.setCiudad(entidad.getCiudad());
        return dto;
    }
}
