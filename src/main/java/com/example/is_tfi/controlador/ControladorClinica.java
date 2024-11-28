package com.example.is_tfi.controlador;

import com.example.is_tfi.dominio.Direccion;
import com.example.is_tfi.dominio.Medico;
import com.example.is_tfi.dominio.Paciente;
import com.example.is_tfi.dto.agregarDiagnosticoDTO;
import com.example.is_tfi.dto.agregarEvolucionDTO;
import com.example.is_tfi.repositorio.impl.RepositorioDiagnosticoImpl;
import com.example.is_tfi.repositorio.impl.RepositorioPacienteImpl;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1")
public class ControladorClinica {
    private final RepositorioPacienteImpl repositorioPaciente = new RepositorioPacienteImpl();
    private final RepositorioDiagnosticoImpl repositorioDiagnostico = new RepositorioDiagnosticoImpl();

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

    @PostMapping("pacientes/{dniPaciente}/diagnosticos")
    public void agregarDiagnostico(@PathVariable Long dniPaciente, @RequestBody agregarDiagnosticoDTO diagnostico) {
        // El controlador tiene la responsabilidad de validar que el diagnostico exista o sea valido
        repositorioDiagnostico.buscarDiagnosticoPorNombre(diagnostico.getNombre()).orElseThrow(() -> new RuntimeException("Diagnostico no encontrado"));
        Paciente paciente = repositorioPaciente.buscarPacientePorDni(dniPaciente).orElseThrow(() -> new RuntimeException("Paciente no encontrado"));
        paciente.agregarDiagnostico(diagnostico.getNombre());
    }

    @PostMapping("pacientes/{dniPaciente}/diagnosticos/{diagnostico}/evoluciones")
    public void agregarEvolucion(@PathVariable Long dniPaciente, @PathVariable String diagnostico, @RequestBody agregarEvolucionDTO evolucion) {
        Paciente paciente = repositorioPaciente.buscarPacientePorDni(dniPaciente).orElseThrow(() -> new RuntimeException("Paciente no encontrado"));
        paciente.agregarEvolucion(diagnostico, evolucion.getInforme(), medico);
    }
}
