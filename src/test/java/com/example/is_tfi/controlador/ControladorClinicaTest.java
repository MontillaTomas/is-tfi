package com.example.is_tfi.controlador;

import com.example.is_tfi.dominio.Direccion;
import com.example.is_tfi.dominio.Medico;
import com.example.is_tfi.dominio.ObraSocial;
import com.example.is_tfi.dominio.Paciente;
import com.example.is_tfi.dto.AgregarEvolucionDTO;
import com.example.is_tfi.dto.PacienteDTO;
import com.example.is_tfi.dto.mapper.DiagnosticoMapper;
import com.example.is_tfi.dto.mapper.MedicamentoMapper;
import com.example.is_tfi.dto.mapper.PacienteMapper;
import com.example.is_tfi.excepciones.DiagnosticoNoEncontradoExcepcion;
import com.example.is_tfi.excepciones.PacienteNoEncontradoExcepcion;
import com.example.is_tfi.repositorio.RepositorioDiagnostico;
import com.example.is_tfi.repositorio.RepositorioMedico;
import com.example.is_tfi.repositorio.RepositorioPaciente;
import com.example.is_tfi.servicio.JwtService;
import com.example.is_tfi.validator.ObraSocialValidator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
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
    private RepositorioDiagnostico repositorioDiagnostico;
    @MockBean
    private RepositorioMedico repositorioMedico;
    @MockBean
    private PacienteMapper pacienteMapper;
    @MockBean
    private MedicamentoMapper medicamentoMapper;
    @MockBean
    private JwtService jwtService;
    @MockBean
    private ObraSocialValidator obraSocialValidator;
    @MockBean
    private DiagnosticoMapper diagnosticoMapper;

    @Autowired
    private ControladorClinica controladorClinica;

    private Paciente paciente;
    private int matriculaMedico;
    private Medico medico;
    private Long dni;
    private String diagnostico;
    private String headerAutorizacion;
    private String informe;

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

        matriculaMedico = 123456;

        medico = new Medico(44747797L,
                20447477972L,
                "Dr House",
                LocalDate.of(2003, 4, 2),
                "doctor.house@gmail.com",
                "+123123123",
                new Direccion("Calle Falsa", 123, "1234", "Springfield"),
                matriculaMedico,
                "Clinico");

        paciente.agregarDiagnostico("Dengue");

        dni = 44747797L;
        diagnostico = "Dengue";
        informe = "El paciente muestra mejora";
        headerAutorizacion = "Bearer token";
    }

    @AfterEach
    void tearDown() {
        paciente = null;
        medico = null;
        dni = null;
        diagnostico = null;
        informe = null;
        headerAutorizacion = null;
    }

    @Test
    void agregarEvolucionConExito() {
        AgregarEvolucionDTO evolucionDTO = new AgregarEvolucionDTO();
        evolucionDTO.setInforme(informe);

        PacienteDTO pacienteDTO = new PacienteDTO();

        Mockito.when(repositorioPaciente.buscarPacientePorDni(dni))
            .thenReturn(Optional.of(paciente));

        Mockito.when(pacienteMapper.toDto(paciente))
            .thenReturn(pacienteDTO);

        Mockito.when(jwtService.extraerMatriculaMedico(headerAutorizacion.substring(7)))
                .thenReturn(matriculaMedico);

        Mockito.when(repositorioMedico.buscarMedicoPorMatricula(matriculaMedico))
                .thenReturn(Optional.of(medico));

        ResponseEntity<PacienteDTO> response = controladorClinica.agregarEvolucion(dni, diagnostico, evolucionDTO, headerAutorizacion);

        assertNotNull(response);
        assertEquals(201, response.getStatusCodeValue());

        Mockito.verify(repositorioPaciente).buscarPacientePorDni(dni);
        Mockito.verify(jwtService).extraerMatriculaMedico(headerAutorizacion.substring(7));
        Mockito.verify(repositorioMedico).buscarMedicoPorMatricula(matriculaMedico);
        Mockito.verify(pacienteMapper).toDto(paciente);
    }

    @Test
    void agregarEvolucionAPacienteInexistente() {
        AgregarEvolucionDTO evolucionDTO = new AgregarEvolucionDTO();
        evolucionDTO.setInforme(informe);

        Mockito.when(repositorioPaciente.buscarPacientePorDni(dni))
            .thenReturn(Optional.empty());

        PacienteNoEncontradoExcepcion exception = assertThrows(PacienteNoEncontradoExcepcion.class, () -> {
            controladorClinica.agregarEvolucion(dni, diagnostico, evolucionDTO, headerAutorizacion);
        });

        assertEquals("Paciente no encontrado", exception.getMessage());
    }

    @Test
    void agregarEvolucionConDiagnosticoInexistente() {
        String diagnosticoInvalido = "Gripe";

        AgregarEvolucionDTO evolucionDTO = new AgregarEvolucionDTO();
        evolucionDTO.setInforme(informe);

        Mockito.when(repositorioPaciente.buscarPacientePorDni(dni))
            .thenReturn(Optional.of(paciente));

        Mockito.when(jwtService.extraerMatriculaMedico(headerAutorizacion.substring(7)))
                .thenReturn(matriculaMedico);

        Mockito.when(repositorioMedico.buscarMedicoPorMatricula(matriculaMedico))
                .thenReturn(Optional.of(medico));

        DiagnosticoNoEncontradoExcepcion excepcion = assertThrows(
            DiagnosticoNoEncontradoExcepcion.class,
            () -> controladorClinica.agregarEvolucion(
                dni,
                diagnosticoInvalido,
                evolucionDTO,
                headerAutorizacion
            )
        );

        assertEquals("Diagnostico no encontrado", excepcion.getMessage());
        Mockito.verify(repositorioPaciente).buscarPacientePorDni(dni);
        Mockito.verify(jwtService).extraerMatriculaMedico(headerAutorizacion.substring(7));
        Mockito.verify(repositorioMedico).buscarMedicoPorMatricula(matriculaMedico);
    }

    @Test
    void obtenerPacientePacienteExistente() {
        Mockito.when(repositorioPaciente.buscarPacientePorDni(dni)).thenReturn(Optional.of(paciente));
        Mockito.when(pacienteMapper.toDto(paciente)).thenReturn(new PacienteDTO());


        ResponseEntity<PacienteDTO> response = controladorClinica.obtenerPaciente(44747797L);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void obtenerPacientePacienteNoExistente() {
        Long dniInvalido = 12345678L;

        Mockito.when(repositorioPaciente.buscarPacientePorDni(dniInvalido)).thenReturn(Optional.empty());

        PacienteNoEncontradoExcepcion exception = assertThrows(PacienteNoEncontradoExcepcion.class, () -> {
            controladorClinica.obtenerPaciente(dniInvalido);
        });

        assertEquals("Paciente no encontrado", exception.getMessage());
    }
}



