package com.example.is_tfi.dto.mapper;

public interface EntidadMapper<E, D> {
    E toEntity(D dto);
    D toDto(E entidad);
}
