package com.example.is_tfi.dto.mapper;

import com.example.is_tfi.dominio.Medico;
import com.example.is_tfi.dto.MedicoDTO;

public class MedicoMapper implements EntidadMapper<Medico, MedicoDTO> {
    private final DireccionMapper direccionMapper;

    public MedicoMapper() {
        this.direccionMapper = new DireccionMapper();
    }

    @Override
    public MedicoDTO toDto(Medico entidad) {
        if (entidad == null) return null;

        MedicoDTO dto = new MedicoDTO();
        dto.setDni(entidad.getDni());
        dto.setCuil(entidad.getCuil());
        dto.setNombre(entidad.getNombre());
        dto.setFechaNacimiento(entidad.getFechaNacimiento());
        dto.setEmail(entidad.getEmail());
        dto.setTelefono(entidad.getTelefono());
        dto.setMatricula(entidad.getMatricula());
        dto.setEspecialidad(entidad.getEspecialidad());
        dto.setDireccion(direccionMapper.toDto(entidad.getDireccion()));

        return dto;
    }

    @Override
    public Medico toEntity(MedicoDTO dto) {
        if (dto == null) return null;

        return new Medico(dto.getDni(),
                dto.getCuil(),
                dto.getNombre(),
                dto.getFechaNacimiento(),
                dto.getEmail(),
                dto.getTelefono(),
                direccionMapper.toEntity(dto.getDireccion()),
                dto.getMatricula(),
                dto.getEspecialidad());
    }
}
