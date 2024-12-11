package com.example.is_tfi.dto.mapper;

import com.example.is_tfi.dominio.Evolucion;
import com.example.is_tfi.dto.EvolucionDTO;
import com.example.is_tfi.dto.PedidoLaboratorioDTO;
import com.example.is_tfi.dto.RecetaDigitalDTO;
import com.example.is_tfi.excepciones.IdYaEstaEnUsoExcepcion;

public class EvolucionMapper {
    private final MedicoMapper medicoMapper;
    private final RecetaDigitalMapper recetaDigitalMapper;
    private final PedidoLaboratorioMapper pedidoLaboratorioMapper;

    public EvolucionMapper() {
        this.medicoMapper = new MedicoMapper();
        this.recetaDigitalMapper = new RecetaDigitalMapper();
        this.pedidoLaboratorioMapper = new PedidoLaboratorioMapper();
    }

    public Evolucion toEntity(EvolucionDTO dto) {
        if (dto == null) {
            return null;
        }

        Evolucion entidad = new Evolucion(dto.getInforme(), medicoMapper.toEntity(dto.getMedico()), dto.getFechaHora());
        for(RecetaDigitalDTO recetaDigitalDTO : dto.getRecetasDigitales()) {
            if(entidad.getRecetasDigitales().containsKey(recetaDigitalDTO.getId())) {
                throw new IdYaEstaEnUsoExcepcion("No se pueden agregar dos recetas digitales con el mismo id");
            }
            entidad.getRecetasDigitales().put(recetaDigitalDTO.getId(), recetaDigitalMapper.toEntity(recetaDigitalDTO));
        }
        for(PedidoLaboratorioDTO pedidoLaboratorioDTO : dto.getPedidosLaboratorio()) {
            if(entidad.getPedidosLaboratorio().containsKey(pedidoLaboratorioDTO.getId())) {
                throw new IdYaEstaEnUsoExcepcion("No se pueden agregar dos pedidos de laboratorio con el mismo id");
            }
            entidad.getPedidosLaboratorio().put(pedidoLaboratorioDTO.getId(), pedidoLaboratorioMapper.toEntity(pedidoLaboratorioDTO));
        }

        return entidad;
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
        dto.setRecetasDigitales(
            entidad.getRecetasDigitales().entrySet().stream()
                .map(entry -> recetaDigitalMapper.toDtoWithId(entry.getValue(), entry.getKey()))
                .toList()
        );
        dto.setPedidosLaboratorio(
            entidad.getPedidosLaboratorio().entrySet().stream()
                .map(entry -> pedidoLaboratorioMapper.toDtoWithId(entry.getValue(), entry.getKey()))
                .toList()
        );

        return dto;
    }
}
