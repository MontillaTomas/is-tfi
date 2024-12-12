package com.example.is_tfi.controlador;

import com.example.is_tfi.dominio.Medico;
import com.example.is_tfi.dominio.Paciente;
import com.example.is_tfi.dto.*;
import com.example.is_tfi.dto.CrearPedidoLaboratorioDTO;
import com.example.is_tfi.dto.mapper.DiagnosticoMapper;
import com.example.is_tfi.dto.mapper.MedicamentoMapper;
import com.example.is_tfi.dto.mapper.PacienteMapper;
import com.example.is_tfi.excepciones.*;
import com.example.is_tfi.repositorio.RepositorioDiagnostico;
import com.example.is_tfi.repositorio.RepositorioMedico;
import com.example.is_tfi.repositorio.RepositorioPaciente;
import com.example.is_tfi.repositorio.impl.RepositorioDiagnosticoImpl;
import com.example.is_tfi.repositorio.impl.RepositorioMedicoImpl;
import com.example.is_tfi.repositorio.impl.RepositorioPacienteImpl;
import com.example.is_tfi.servicio.JwtService;
import com.example.is_tfi.validator.ObraSocialValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ControladorClinica {
    private final RepositorioPaciente repositorioPaciente;
    private final RepositorioDiagnostico repositorioDiagnostico;
    private final RepositorioMedico repositorioMedico;
    private final PacienteMapper pacienteMapper;
    private final MedicamentoMapper medicamentoMapper;
    private final JwtService jwtService;
    private final ObraSocialValidator obraSocialValidator;
    private final DiagnosticoMapper diagnosticoMapper;

    @Autowired
    public ControladorClinica(
            RepositorioPaciente repositorioPaciente,
            RepositorioDiagnostico repositorioDiagnostico,
            RepositorioMedico repositorioMedico,
            PacienteMapper pacienteMapper,
            MedicamentoMapper medicamentoMapper,
            JwtService jwtService,
            ObraSocialValidator obraSocialValidator,
            DiagnosticoMapper diagnosticoMapper) {
        this.repositorioPaciente = repositorioPaciente;
        this.repositorioDiagnostico = repositorioDiagnostico;
        this.repositorioMedico = repositorioMedico;
        this.pacienteMapper = pacienteMapper;
        this.medicamentoMapper = medicamentoMapper;
        this.jwtService = jwtService;
        this.obraSocialValidator = obraSocialValidator;
        this.diagnosticoMapper = diagnosticoMapper;
    }

    private Medico obtenerMedicoLogueado(String headerAutorizacion) {
        String token = headerAutorizacion.substring(7);
        int matricula = jwtService.extraerMatriculaMedico(token);
        return repositorioMedico.buscarMedicoPorMatricula(matricula)
                .orElseThrow(() -> new MedicoNoEncontradoExcepcion("Medico no encontrado"));
    }

    @GetMapping("pacientes/{dni}")
    public ResponseEntity<PacienteDTO> obtenerPaciente(@PathVariable Long dni) {
        return repositorioPaciente.buscarPacientePorDni(dni)
                .map(pacienteMapper::toDto)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new PacienteNoEncontradoExcepcion("Paciente no encontrado"));
    }

    @GetMapping("pacientes")
    public ResponseEntity<List<PacienteDTO>> obtenerPacientes(@RequestParam(required = false) String busqueda) {
        List<PacienteDTO> pacientes = (busqueda != null && !busqueda.isEmpty())
                ? repositorioPaciente.buscarPacientesPorTexto(busqueda).stream()
                .map(pacienteMapper::toDto)
                .toList()
                : pacienteMapper.toDto(repositorioPaciente.obtenerPacientes());
        return ResponseEntity.ok(pacientes);
    }

    @PostMapping("pacientes")
    public ResponseEntity<PacienteDTO> crearPaciente(@Valid @RequestBody CrearPacienteDTO dto) {
        repositorioPaciente.buscarPacientePorDni(dto.getDni()).ifPresent(p -> {
            throw new DniEnUsoExcepcion("Ya existe un paciente con ese DNI");
        });
        repositorioPaciente.buscarPacientePorCuil(dto.getCuil()).ifPresent(p -> {
            throw new CuilEnUsoExcepcion("Ya existe un paciente con ese CUIL");
        });
        Paciente paciente = pacienteMapper.toEntity(dto);
        repositorioPaciente.guardarPaciente(paciente);
        return ResponseEntity.status(201).body(pacienteMapper.toDto(paciente));
    }

    @PostMapping("pacientes/{dniPaciente}/diagnosticos")
    public ResponseEntity<PacienteDTO> agregarDiagnostico(@PathVariable Long dniPaciente,
                                                          @Valid @RequestBody AgregarDiagnosticoDTO diagnostico) {
        repositorioDiagnostico.buscarDiagnosticoPorNombre(diagnostico.getNombre())
                .orElseThrow(() -> new DiagnosticoNoEncontradoExcepcion("Diagnostico no encontrado"));
        Paciente paciente = repositorioPaciente.buscarPacientePorDni(dniPaciente)
                .orElseThrow(() -> new PacienteNoEncontradoExcepcion("Paciente no encontrado"));
        paciente.agregarDiagnostico(diagnostico.getNombre());
        return ResponseEntity.status(201).body(pacienteMapper.toDto(paciente));
    }

    @PostMapping("pacientes/{dniPaciente}/diagnosticos/{diagnostico}/evoluciones")
    public ResponseEntity<PacienteDTO> agregarEvolucion(@PathVariable Long dniPaciente,
                                                        @PathVariable String diagnostico,
                                                        @Valid @RequestBody AgregarEvolucionDTO evolucion,
                                                        @RequestHeader("Authorization") String headerAutorizacion) {
        Paciente paciente = repositorioPaciente.buscarPacientePorDni(dniPaciente)
                .orElseThrow(() -> new PacienteNoEncontradoExcepcion("Paciente no encontrado"));
        Medico medicoLogueado = obtenerMedicoLogueado(headerAutorizacion);
        paciente.agregarEvolucion(diagnostico, evolucion.getInforme(), medicoLogueado);
        return ResponseEntity.status(201).body(pacienteMapper.toDto(paciente));
    }


    @PostMapping("pacientes/{dniPaciente}/diagnosticos/{diagnostico}/evoluciones/{idEvolucion}/recetas-digitales")
    public ResponseEntity<PacienteDTO> crearRecetaDigital(@PathVariable Long dniPaciente,
                                                          @PathVariable String diagnostico,
                                                          @PathVariable Long idEvolucion,
                                                          @Valid @RequestBody List<@Valid MedicamentoDTO> medicamentos,
                                                          @RequestHeader("Authorization") String headerAutorizacion) {
        Paciente paciente = repositorioPaciente.buscarPacientePorDni(dniPaciente)
                .orElseThrow(() -> new PacienteNoEncontradoExcepcion("Paciente no encontrado"));
        Medico medicoLogueado = obtenerMedicoLogueado(headerAutorizacion);
        if (!obraSocialValidator.obraSocialEsValida(paciente.getObraSocial().getCodigo())) {
            throw new ObraSocialNoValidaExcepcion("Obra social no válida");
        }
        paciente.crearRecetaDigital(diagnostico, idEvolucion, medicamentoMapper.toEntity(medicamentos), medicoLogueado);
        return ResponseEntity.status(201).body(pacienteMapper.toDto(paciente));
    }

    @PostMapping("pacientes/{dniPaciente}/diagnosticos/{diagnostico}/evoluciones/{idEvolucion}/pedidos-laboratorio")
    public ResponseEntity<PacienteDTO> crearPedidoLaboratorio(@PathVariable Long dniPaciente,
                                                              @PathVariable String diagnostico,
                                                              @PathVariable Long idEvolucion,
                                                              @Valid @RequestBody CrearPedidoLaboratorioDTO dto,
                                                              @RequestHeader("Authorization") String headerAutorizacion) {
        Paciente paciente = repositorioPaciente.buscarPacientePorDni(dniPaciente)
                .orElseThrow(() -> new PacienteNoEncontradoExcepcion("Paciente no encontrado"));
        Medico medicoLogueado = obtenerMedicoLogueado(headerAutorizacion);
        paciente.crearPedidoLaboratorio(diagnostico, idEvolucion, dto.getTexto(), medicoLogueado);
        return ResponseEntity.status(201).body(pacienteMapper.toDto(paciente));
    }

    @GetMapping("diagnosticos")
    public ResponseEntity<List<DiagnosticoDTO>> obtenerDiagnosticos(@RequestParam(required = false) String busqueda) {
        List<DiagnosticoDTO> diagnosticos = (busqueda != null && !busqueda.isEmpty())
                ? repositorioDiagnostico.buscarDiagnosticoPorNombre(busqueda).stream()
                .map(diagnosticoMapper::toDto)
                .toList()
                : diagnosticoMapper.toDto(repositorioDiagnostico.obtenerDiagnostico());
        return ResponseEntity.ok(diagnosticos);
    }
}
