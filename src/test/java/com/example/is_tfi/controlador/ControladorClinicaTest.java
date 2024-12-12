package com.example.is_tfi.controlador;

import com.example.is_tfi.dominio.Direccion;
import com.example.is_tfi.dominio.ObraSocial;
import com.example.is_tfi.dominio.Paciente;
import com.example.is_tfi.dto.PacienteDTO;
import com.example.is_tfi.dto.mapper.PacienteMapper;
import com.example.is_tfi.excepciones.PacienteNoEncontradoExcepcion;
import com.example.is_tfi.repositorio.RepositorioPaciente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest

class ControladorClinicaTest {

    @MockBean
    private RepositorioPaciente repositorioPaciente;

    @MockBean
    private PacienteMapper pacienteMapper;

    @Autowired
    private ControladorClinica controladorClinica;

    private Paciente paciente;

    @BeforeEach
    void setUp() {
        paciente = new Paciente(44747797L,
                20447477972L,
                "Tomas Montilla",
                LocalDate.of(2003, 4, 2),
                "montilla.tom1@gmail.com",
                "+123123123",
                new ObraSocial(119708, "OBRA SOCIAL DEL PERSONAL DE SEGURIDAD", "OSPSIP"),
                123456,
                new Direccion("Calle Falsa", 123, "1234", "Springfield"));
    }

    @Test
    void obtenerPacientePacienteExistente() {
        Mockito.when(repositorioPaciente.buscarPacientePorDni(44747797L)).thenReturn(Optional.of(paciente));
        Mockito.when(pacienteMapper.toDto(paciente)).thenReturn(new PacienteDTO());


        ResponseEntity<PacienteDTO> response = controladorClinica.obtenerPaciente(44747797L);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void obtenerPacientePacienteNoExistente() {

        Mockito.when(repositorioPaciente.buscarPacientePorDni(12345678L)).thenReturn(Optional.empty());


        PacienteNoEncontradoExcepcion exception = assertThrows(PacienteNoEncontradoExcepcion.class, () -> {
            controladorClinica.obtenerPaciente(12345678L);
        });
        ResponseEntity<PacienteDTO> response = controladorClinica.obtenerPaciente(44747797L);

        assertEquals("Paciente no encontrado", exception.getMessage());

    }





}



