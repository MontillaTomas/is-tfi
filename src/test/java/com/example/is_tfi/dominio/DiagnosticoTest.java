package com.example.is_tfi.dominio;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class DiagnosticoTest {
    public Diagnostico diagnostico;
    private Medico medico;
    private Direccion direccion;

    @BeforeEach
    public void setUp(){
        this.diagnostico = new Diagnostico("Gripe");
        this.direccion = new Direccion("Santa Fe", 3800, "4000", "San Miguel de Tucuman");
        this.medico = new Medico(44478902L, 1244478902L, "Dr. House", LocalDate.now(), "house@gmail.com", "381485642", direccion ,155, "Pediatria");
        diagnostico.agregarEvolucion("Informe 1", medico);
    }

    @AfterEach
    public void tearDown(){
        diagnostico = null;
    }

    @Test
    void agregarEvolucionADiagnosticoSinEvoluciones() {
        assertEquals(1, diagnostico.getEvoluciones().size());
        assertTrue(diagnostico.getEvoluciones().containsKey(1L));
        Evolucion evolucion = diagnostico.getEvoluciones().get(1L);
        assertEquals("Informe 1", evolucion.getInforme());
        assertEquals(medico, evolucion.getMedico());
    }

    @Test
    void agregarEvolucionADiagnosticoConUnaEvolucion() {
        diagnostico.agregarEvolucion("Informe 2", medico);
        assertEquals(2, diagnostico.getEvoluciones().size());
        assertTrue(diagnostico.getEvoluciones().containsKey(2L));
        Evolucion evolucion = diagnostico.getEvoluciones().get(2L);
        assertEquals("Informe 2", evolucion.getInforme());
        assertEquals(medico, evolucion.getMedico());
    }

    @Test
    void crearPedidoLaboratorioDebeLanzarExcepcionCuandoEvolucionNoExiste() {
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                diagnostico.crearPedidoLaboratorio(999L, "Texto", medico)
        );

        assertEquals("No se encontró la evolución con el id 999", exception.getMessage());
    }

    @Test
    void crearRecetaDigitalDebeLanzarExcepcionCuandoEvolucionNoExiste() {
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                diagnostico.crearRecetaDigital(999L, List.of(), medico)
        );

        assertEquals("No se encontró la evolución con el id 999", exception.getMessage());
    }
}