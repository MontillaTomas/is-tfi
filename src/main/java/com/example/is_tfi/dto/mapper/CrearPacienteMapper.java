package com.example.is_tfi.dto.mapper;

import com.example.is_tfi.dominio.Paciente;
import com.example.is_tfi.dto.CrearPacienteDTO;

public class CrearPacienteMapper implements EntidadMapper<Paciente, CrearPacienteDTO> {
    private final ObraSocialMapper obraSocialMapper;
    private final DireccionMapper direccionMapper;

    public CrearPacienteMapper() {
        this.obraSocialMapper = new ObraSocialMapper();
        this.direccionMapper = new DireccionMapper();
    }

    @Override
    public Paciente toEntity(CrearPacienteDTO dto) {
        if (dto == null) return null;

        return new Paciente(
            dto.getDni(),
            dto.getCuil(),
            dto.getNombre(),
            dto.getFechaNacimiento(),
            dto.getEmail(),
            dto.getTelefono(),
            obraSocialMapper.toEntity(dto.getObraSocial()),
            dto.getNumeroAfiliadoObraSocial(),
            direccionMapper.toEntity(dto.getDireccion())
        );
    }

    @Override
    public CrearPacienteDTO toDto(Paciente entidad) {
        if (entidad == null) return null;

        CrearPacienteDTO crearPacienteDTO = new CrearPacienteDTO();
        crearPacienteDTO.setDni(entidad.getDni());
        crearPacienteDTO.setCuil(entidad.getCuil());
        crearPacienteDTO.setNombre(entidad.getNombre());
        crearPacienteDTO.setFechaNacimiento(entidad.getFechaNacimiento());
        crearPacienteDTO.setEmail(entidad.getEmail());
        crearPacienteDTO.setTelefono(entidad.getTelefono());
        crearPacienteDTO.setDireccion(direccionMapper.toDto(entidad.getDireccion()));
        crearPacienteDTO.setObraSocial(obraSocialMapper.toDto(entidad.getObraSocial()));
        crearPacienteDTO.setNumeroAfiliadoObraSocial(entidad.getNumeroAfiliadoObraSocial());

        return crearPacienteDTO;
    }
}
