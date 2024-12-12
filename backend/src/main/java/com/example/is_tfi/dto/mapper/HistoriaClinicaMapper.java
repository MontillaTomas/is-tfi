package com.example.is_tfi.dto.mapper;

import com.example.is_tfi.dominio.Diagnostico;
import com.example.is_tfi.dominio.HistoriaClinica;
import com.example.is_tfi.dto.DiagnosticoDTO;
import com.example.is_tfi.dto.HistoriaClinicaDTO;

import java.util.ArrayList;
import java.util.List;

public class HistoriaClinicaMapper implements EntidadMapper<HistoriaClinica, HistoriaClinicaDTO> {
    private final DiagnosticoMapper diagnosticoMapper;

    public HistoriaClinicaMapper() {
        this.diagnosticoMapper = new DiagnosticoMapper();
    }


    @Override
    public HistoriaClinica toEntity(HistoriaClinicaDTO dto) {
        if (dto == null) return null;

        HistoriaClinica historiaClinica = new HistoriaClinica();
        List<Diagnostico> diagnosticos = new ArrayList<>();
        for (DiagnosticoDTO diagnosticoDto : dto.getDiagnosticos()) {
            diagnosticos.add(diagnosticoMapper.toEntity(diagnosticoDto));
        }
        historiaClinica.setDiagnosticos(diagnosticos);

        return historiaClinica;
    }

    @Override
    public HistoriaClinicaDTO toDto(HistoriaClinica entidad) {
        if (entidad == null) return null;

        HistoriaClinicaDTO historiaClinicaDTO = new HistoriaClinicaDTO();
        List<DiagnosticoDTO> diagnosticos = new ArrayList<>();
        for (Diagnostico diagnostico : entidad.getDiagnosticos()) {
            diagnosticos.add(diagnosticoMapper.toDto(diagnostico));
        }
        historiaClinicaDTO.setDiagnosticos(diagnosticos);

        return historiaClinicaDTO;
    }
}
