package com.example.is_tfi.dto.mapper;

import com.example.is_tfi.dominio.ObraSocial;
import com.example.is_tfi.dto.ObraSocialDTO;

public class ObraSocialMapper implements EntidadMapper<ObraSocial, ObraSocialDTO> {

    @Override
    public ObraSocial toEntity(ObraSocialDTO dto) {
        if (dto == null) return null;

        return new ObraSocial(dto.getCodigo(), dto.getDenominacion(), dto.getSigla());
    }

    @Override
    public ObraSocialDTO toDto(ObraSocial entidad) {
        if (entidad == null) return null;

        ObraSocialDTO obraSocialDTO = new ObraSocialDTO();
        obraSocialDTO.setCodigo(entidad.getCodigo());
        obraSocialDTO.setDenominacion(entidad.getDenominacion());
        obraSocialDTO.setSigla(entidad.getSigla());

        return obraSocialDTO;
    }
}
