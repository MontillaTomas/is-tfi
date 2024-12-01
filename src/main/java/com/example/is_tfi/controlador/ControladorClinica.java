package com.example.is_tfi.controlador;

import com.example.is_tfi.dominio.Direccion;
import com.example.is_tfi.dominio.Medicamento;
import com.example.is_tfi.dominio.Medico;
import com.example.is_tfi.dominio.Paciente;
import com.example.is_tfi.dto.*;
import com.example.is_tfi.dto.mapper.CrearPacienteMapper;
import com.example.is_tfi.dto.mapper.CrearPedidoLaboratorioDTO;
import com.example.is_tfi.dto.mapper.MedicamentoMapper;
import com.example.is_tfi.dto.mapper.PacienteMapper;
import com.example.is_tfi.repositorio.impl.RepositorioDiagnosticoImpl;
import com.example.is_tfi.repositorio.impl.RepositorioPacienteImpl;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ControladorClinica {
    private final RepositorioPacienteImpl repositorioPaciente = new RepositorioPacienteImpl();
    private final RepositorioDiagnosticoImpl repositorioDiagnostico = new RepositorioDiagnosticoImpl();
    private final PacienteMapper pacienteMapper = new PacienteMapper();
    private final CrearPacienteMapper crearPacienteMapper = new CrearPacienteMapper();
    private final MedicamentoMapper medicamentoMapper = new MedicamentoMapper();

    // Por ahora se instancia un objeto medico
    // Cuando se implemente la autenticacion se debera obtener el medico logueado
    private final Medico medico = new Medico(44747797L,
            20447477972L,
            "Dr House",
            LocalDate.of(2003, 4, 2),
            "doctor.house@gmail.com",
            "+123123123",
            new Direccion("Calle Falsa", 123, "1234", "Springfield"),
            123456,
            "Clinico");

    @GetMapping("pacientes/{dni}")
    public PacienteDTO obtenerPaciente(@PathVariable Long dni) {
        return repositorioPaciente.buscarPacientePorDni(dni)
                .map(pacienteMapper::toDto)
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));
    }

    @GetMapping("pacientes")
    public List<PacienteDTO> obtenerPacientes(@RequestParam(required = false) String busqueda) {
        if(busqueda != null && !busqueda.isEmpty()) {
            return repositorioPaciente.buscarPacientesPorTexto(busqueda).stream()
                    .map(pacienteMapper::toDto)
                    .toList();
        }
        return pacienteMapper.toDto(repositorioPaciente.obtenerPacientes());
    }

    @PostMapping("pacientes")
    public PacienteDTO crearPaciente(@RequestBody  CrearPacienteDTO dto) {
        repositorioPaciente.buscarPacientePorDni(dto.getDni()).ifPresent(paciente -> {
            throw new RuntimeException("Ya existe un paciente con ese DNI");
        });
        repositorioPaciente.buscarPacientePorCuil(dto.getCuil()).ifPresent(paciente -> {
            throw new RuntimeException("Ya existe un paciente con ese CUIL");
        });
        Paciente paciente = crearPacienteMapper.toEntity(dto);
        repositorioPaciente.guardarPaciente(paciente);
        return pacienteMapper.toDto(paciente);
    }

    @PostMapping("pacientes/{dniPaciente}/diagnosticos")
    public PacienteDTO agregarDiagnostico(@PathVariable Long dniPaciente, @RequestBody AgregarDiagnosticoDTO diagnostico) {
        // El controlador tiene la responsabilidad de validar que el diagnostico exista o sea valido
        repositorioDiagnostico.buscarDiagnosticoPorNombre(diagnostico.getNombre()).orElseThrow(() -> new RuntimeException("Diagnostico no encontrado"));
        Paciente paciente = repositorioPaciente.buscarPacientePorDni(dniPaciente).orElseThrow(() -> new RuntimeException("Paciente no encontrado"));
        paciente.agregarDiagnostico(diagnostico.getNombre());
        return pacienteMapper.toDto(paciente);
    }

    @PostMapping("pacientes/{dniPaciente}/diagnosticos/{diagnostico}/evoluciones")
    public PacienteDTO agregarEvolucion(@PathVariable Long dniPaciente, @PathVariable String diagnostico, @RequestBody AgregarEvolucionDTO evolucion) {
        Paciente paciente = repositorioPaciente.buscarPacientePorDni(dniPaciente).orElseThrow(() -> new RuntimeException("Paciente no encontrado"));
        paciente.agregarEvolucion(diagnostico, evolucion.getInforme(), medico);
        return pacienteMapper.toDto(paciente);
    }

    @PostMapping("pacientes/{dniPaciente}/diagnosticos/{diagnostico}/evoluciones/{idEvolucion}/recetas-digitales")
    public PacienteDTO crearRecetaDigital(
            @PathVariable Long dniPaciente,
            @PathVariable String diagnostico,
            @PathVariable Long idEvolucion,
            @RequestBody List<MedicamentoDTO> medicamentos) {
        Paciente paciente = repositorioPaciente.buscarPacientePorDni(dniPaciente).orElseThrow(() -> new RuntimeException("Paciente no encontrado"));
        paciente.crearRecetaDigital(diagnostico, idEvolucion, medicamentoMapper.toEntity(medicamentos), medico);
        return pacienteMapper.toDto(paciente);
    }

    @PostMapping("pacientes/{dniPaciente}/diagnosticos/{diagnostico}/evoluciones/{idEvolucion}/pedidos-laboratorio")
    public PacienteDTO crearPedidoLaboratorio(
            @PathVariable Long dniPaciente,
            @PathVariable String diagnostico,
            @PathVariable Long idEvolucion,
            @RequestBody CrearPedidoLaboratorioDTO dto) {
        Paciente paciente = repositorioPaciente.buscarPacientePorDni(dniPaciente).orElseThrow(() -> new RuntimeException("Paciente no encontrado"));
        paciente.crearPedidoLaboratorio(diagnostico, idEvolucion, dto.getTexto(), medico);
        return pacienteMapper.toDto(paciente);
    }
}
