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

    public ControladorClinica(JwtService jwtService, ObraSocialValidator obraSocialValidator) {
        this.repositorioPaciente = new RepositorioPacienteImpl();
        this.repositorioDiagnostico = new RepositorioDiagnosticoImpl();
        this.repositorioMedico = new RepositorioMedicoImpl();
        this.pacienteMapper = new PacienteMapper();
        this.medicamentoMapper = new MedicamentoMapper();
        this.jwtService = jwtService;
        this.obraSocialValidator = obraSocialValidator;
        this.diagnosticoMapper = new DiagnosticoMapper();
    }

    private Medico obtenerMedicoLogueado(String headerAutorizacion) {
        String token = headerAutorizacion.substring(7);
        int matricula = jwtService.extraerMatriculaMedico(token);
        return repositorioMedico.buscarMedicoPorMatricula(matricula)
                .orElseThrow(() -> new MedicoNoEncontradoExcepcion("Medico no encontrado"));
    }

    @GetMapping("pacientes/{dni}")
    public PacienteDTO obtenerPaciente(@PathVariable Long dni) throws PacienteNoEncontradoExcepcion {
        return repositorioPaciente.buscarPacientePorDni(dni)
                .map(pacienteMapper::toDto)
                .orElseThrow(() -> new PacienteNoEncontradoExcepcion("Paciente no encontrado"));
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
    public PacienteDTO crearPaciente(@Valid @RequestBody  CrearPacienteDTO dto) throws DniEnUsoExcepcion, CuilEnUsoExcepcion {
        repositorioPaciente.buscarPacientePorDni(dto.getDni()).ifPresent(paciente -> {
            throw new DniEnUsoExcepcion("Ya existe un paciente con ese DNI");
        });
        repositorioPaciente.buscarPacientePorCuil(dto.getCuil()).ifPresent(paciente -> {
            throw new CuilEnUsoExcepcion("Ya existe un paciente con ese CUIL");
        });
        Paciente paciente = pacienteMapper.toEntity(dto);
        repositorioPaciente.guardarPaciente(paciente);
        return pacienteMapper.toDto(paciente);
    }

    @PostMapping("pacientes/{dniPaciente}/diagnosticos")
    public PacienteDTO agregarDiagnostico(@PathVariable Long dniPaciente,@Valid @RequestBody AgregarDiagnosticoDTO diagnostico) throws PacienteNoEncontradoExcepcion, DiagnosticoNoEncontradoExcepcion {
        // El controlador tiene la responsabilidad de validar que el diagnostico exista o sea valido
        repositorioDiagnostico.buscarDiagnosticoPorNombre(diagnostico.getNombre()).orElseThrow(() -> new DiagnosticoNoEncontradoExcepcion("Diagnostico no encontrado"));
        Paciente paciente = repositorioPaciente.buscarPacientePorDni(dniPaciente).orElseThrow(() -> new PacienteNoEncontradoExcepcion("Paciente no encontrado"));
        paciente.agregarDiagnostico(diagnostico.getNombre());
        return pacienteMapper.toDto(paciente);
    }

    @PostMapping("pacientes/{dniPaciente}/diagnosticos/{diagnostico}/evoluciones")
    public PacienteDTO agregarEvolucion(
        @PathVariable Long dniPaciente,
        @PathVariable String diagnostico,
        @Valid @RequestBody AgregarEvolucionDTO evolucion,
        @RequestHeader("Authorization") String headerAutorizacion) throws PacienteNoEncontradoExcepcion{
        Paciente paciente = repositorioPaciente.buscarPacientePorDni(dniPaciente).orElseThrow(() -> new PacienteNoEncontradoExcepcion("Paciente no encontrado"));
        Medico medicoLogueado = obtenerMedicoLogueado(headerAutorizacion);
        paciente.agregarEvolucion(diagnostico, evolucion.getInforme(), medicoLogueado);
        return pacienteMapper.toDto(paciente);
    }

    @PostMapping("pacientes/{dniPaciente}/diagnosticos/{diagnostico}/evoluciones/{idEvolucion}/recetas-digitales")
    public PacienteDTO crearRecetaDigital(
            @PathVariable Long dniPaciente,
            @PathVariable String diagnostico,
            @PathVariable Long idEvolucion,
            @Valid @RequestBody List<@Valid MedicamentoDTO> medicamentos,
            @RequestHeader("Authorization") String headerAutorizacion) throws PacienteNoEncontradoExcepcion{
        Paciente paciente = repositorioPaciente.buscarPacientePorDni(dniPaciente).orElseThrow(() -> new PacienteNoEncontradoExcepcion("Paciente no encontrado"));
        Medico medicoLogueado = obtenerMedicoLogueado(headerAutorizacion);
        int codigoObraSocial = paciente.getObraSocial().getCodigo();
        if (!obraSocialValidator.obraSocialEsValida(codigoObraSocial)) {
            throw new ObraSocialNoValidaExcepcion("Obra social no válida");
        }
        paciente.crearRecetaDigital(diagnostico, idEvolucion, medicamentoMapper.toEntity(medicamentos), medicoLogueado);
        return pacienteMapper.toDto(paciente);
    }

    @PostMapping("pacientes/{dniPaciente}/diagnosticos/{diagnostico}/evoluciones/{idEvolucion}/pedidos-laboratorio")
    public PacienteDTO crearPedidoLaboratorio(
            @PathVariable Long dniPaciente,
            @PathVariable String diagnostico,
            @PathVariable Long idEvolucion,
            @Valid @RequestBody CrearPedidoLaboratorioDTO dto,
            @RequestHeader("Authorization") String headerAutorizacion) throws PacienteNoEncontradoExcepcion {
        Paciente paciente = repositorioPaciente.buscarPacientePorDni(dniPaciente).orElseThrow(() -> new PacienteNoEncontradoExcepcion("Paciente no encontrado"));
        Medico medicoLogueado = obtenerMedicoLogueado(headerAutorizacion);
        paciente.crearPedidoLaboratorio(diagnostico, idEvolucion, dto.getTexto(), medicoLogueado);
        return pacienteMapper.toDto(paciente);
    }

    @GetMapping("diagnosticos")
    public List<DiagnosticoDTO> obtenerDiagnosticos(@RequestParam(required = false) String busqueda){
        if(busqueda != null && !busqueda.isEmpty()){
            return repositorioDiagnostico.buscarDiagnosticoPorNombre(busqueda).stream()
                    .map(diagnosticoMapper::toDto)
                    .toList();
        }
        return diagnosticoMapper.toDto(repositorioDiagnostico.obtenerDiagnostico());
    }
}
