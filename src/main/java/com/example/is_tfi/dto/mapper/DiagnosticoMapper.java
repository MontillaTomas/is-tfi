package com.example.is_tfi.dto.mapper;

import com.example.is_tfi.dominio.Diagnostico;
import com.example.is_tfi.dto.DiagnosticoDTO;
import com.example.is_tfi.dto.EvolucionDTO;
import com.example.is_tfi.excepciones.IdYaEstaEnUsoExcepcion;

public class DiagnosticoMapper implements EntidadMapper<Diagnostico, DiagnosticoDTO> {
    private final EvolucionMapper evolucionMapper;

    public DiagnosticoMapper() {
        this.evolucionMapper = new EvolucionMapper();
    }

    @Override
    public Diagnostico toEntity(DiagnosticoDTO dto) {
        if (dto == null) return null;

        Diagnostico diagnostico = new Diagnostico(dto.getNombre());

        for(EvolucionDTO evolucionDTO : dto.getEvoluciones()) {
            if (diagnostico.getEvoluciones().containsKey(evolucionDTO.getId())) {
                throw new IdYaEstaEnUsoExcepcion("No se pueden agregar dos evoluciones con el mismo id");
            }

            diagnostico.getEvoluciones().put(evolucionDTO.getId(), evolucionMapper.toEntity(evolucionDTO));
        }

        return diagnostico;
    }

    @Override
    public DiagnosticoDTO toDto(Diagnostico entidad) {
        if (entidad == null) return null;

        DiagnosticoDTO dto = new DiagnosticoDTO();
        dto.setNombre(entidad.getNombre());
        dto.setEvoluciones(
            entidad.getEvoluciones().entrySet().stream()
                .map(entry -> evolucionMapper.toDtoWithId(entry.getValue(), entry.getKey()))
                .toList()
        );

        return dto;
    }
}
