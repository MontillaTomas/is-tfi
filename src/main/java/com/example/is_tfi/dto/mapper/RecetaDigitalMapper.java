package com.example.is_tfi.dto.mapper;

import com.example.is_tfi.dominio.RecetaDigital;
import com.example.is_tfi.dto.RecetaDigitalDTO;

public class RecetaDigitalMapper{
    private MedicamentoMapper medicamentoMapper;
    private MedicoMapper medicoMapper;

    public RecetaDigitalMapper() {
        this.medicamentoMapper = new MedicamentoMapper();
        this.medicoMapper = new MedicoMapper();
    }

    public RecetaDigital toEntity(RecetaDigitalDTO dto) {
        if (dto == null) return null;

        RecetaDigital entidad = new RecetaDigital(
            medicamentoMapper.toEntity(dto.getMedicamentos()),
            medicoMapper.toEntity(dto.getMedico())
        );
        entidad.setFechaHora(dto.getFechaHora());

        return entidad;
    }

    public RecetaDigitalDTO toDtoWithId(RecetaDigital entidad, Long id) {
        if (entidad == null) return null;

        RecetaDigitalDTO dto = new RecetaDigitalDTO();
        dto.setId(id);
        dto.setMedicamentos(medicamentoMapper.toDto(entidad.getMedicamentos()));
        dto.setMedico(medicoMapper.toDto(entidad.getMedico()));
        dto.setFechaHora(entidad.getFechaHora());

        return dto;
    }
}
