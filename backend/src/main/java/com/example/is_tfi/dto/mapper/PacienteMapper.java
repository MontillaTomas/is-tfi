package com.example.is_tfi.dto.mapper;

import com.example.is_tfi.dominio.Paciente;
import com.example.is_tfi.dto.CrearPacienteDTO;
import com.example.is_tfi.dto.PacienteDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PacienteMapper implements EntidadMapper<Paciente, PacienteDTO> {
    private final HistoriaClinicaMapper historiaClinicaMapper;
    private final DireccionMapper direccionMapper;
    private final ObraSocialMapper obraSocialMapper;

    public PacienteMapper() {
        this.historiaClinicaMapper = new HistoriaClinicaMapper();
        this.direccionMapper = new DireccionMapper();
        this.obraSocialMapper = new ObraSocialMapper();
    }

    @Override
    public Paciente toEntity(PacienteDTO dto) {
        if (dto == null) return null;

        Paciente paciente = new Paciente(
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
        paciente.setHistoriaClinica(historiaClinicaMapper.toEntity(dto.getHistoriaClinica()));

        return paciente;
    }
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
    public PacienteDTO toDto(Paciente entidad) {
        if (entidad == null) return null;

        PacienteDTO pacienteDTO = new PacienteDTO();
        pacienteDTO.setDni(entidad.getDni());
        pacienteDTO.setCuil(entidad.getCuil());
        pacienteDTO.setNombre(entidad.getNombre());
        pacienteDTO.setFechaNacimiento(entidad.getFechaNacimiento());
        pacienteDTO.setEmail(entidad.getEmail());
        pacienteDTO.setTelefono(entidad.getTelefono());
        pacienteDTO.setDireccion(direccionMapper.toDto(entidad.getDireccion()));
        pacienteDTO.setObraSocial(obraSocialMapper.toDto(entidad.getObraSocial()));
        pacienteDTO.setNumeroAfiliadoObraSocial(entidad.getNumeroAfiliadoObraSocial());
        pacienteDTO.setHistoriaClinica(historiaClinicaMapper.toDto(entidad.getHistoriaClinica()));

        return pacienteDTO;
    }

    public List<PacienteDTO> toDto(List<Paciente> entidades) {
        return entidades.stream().map(this::toDto).toList();
    }
}
