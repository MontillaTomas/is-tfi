package com.example.is_tfi.dto.mapper;

import com.example.is_tfi.dominio.Evolucion;
import com.example.is_tfi.dto.EvolucionDTO;
public class EvolucionMapper {
    private final MedicoMapper medicoMapper;

    public EvolucionMapper() {
        this.medicoMapper = new MedicoMapper();
    }

    public Evolucion toEntity(EvolucionDTO dto) {
        if (dto == null) {
            return null;
        }

        return new Evolucion(dto.getInforme(), medicoMapper.toEntity(dto.getMedico()), dto.getFechaHora());
    }

    public EvolucionDTO toDtoWithId(Evolucion entidad, Long id) {
        if (entidad == null) {
            return null;
        }

        EvolucionDTO dto = new EvolucionDTO();
        dto.setInforme(entidad.getInforme());
        dto.setMedico(medicoMapper.toDto(entidad.getMedico()));
        dto.setFechaHora(entidad.getFechaHora());
        dto.setId(id);

        return dto;
    }
}
