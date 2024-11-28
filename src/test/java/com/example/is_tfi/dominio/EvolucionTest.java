package com.example.is_tfi.dominio;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EvolucionTest {
    private Direccion direccion;
    private Evolucion evolucion;
    private Medico medico;

    @BeforeEach
    public void setUp(){
        direccion = new Direccion("Santa Fe", 3800, "4000", "San Miguel de Tucuman");
        medico = new Medico(44478902L, 1244478902L, "Dr. House", LocalDate.now(), "house@gmail.com", "381485642", direccion ,155, "Pediatria");
        evolucion = new Evolucion("informe 1", medico);
    }

    @AfterEach
    public void tearDown(){
        evolucion = null;
    }

    @Test
    void agregarUnPedidoDeLaboratorioAEvolucion() {
        evolucion.crearPedidoLaboratorio("Analisis de sangre", medico);

        assertEquals(1, evolucion.getPedidosLaboratorio().size());
        assertTrue(evolucion.getPedidosLaboratorio().containsKey(1L));

        PedidoLaboratorio pedidoLaboratorio = evolucion.getPedidosLaboratorio().get(1L);
        assertEquals("Analisis de sangre", pedidoLaboratorio.getTexto());
        assertEquals(medico, pedidoLaboratorio.getMedico());
    }

    @Test
    void agregarMultiplesPedidosDeLaboratorioAEvolucion() {
        evolucion.crearPedidoLaboratorio("Analisis de sangre", medico);
        evolucion.crearPedidoLaboratorio("Analisis de orina", medico);
        assertEquals(2, evolucion.getPedidosLaboratorio().size());
        assertTrue(evolucion.getPedidosLaboratorio().containsKey(2L));
        PedidoLaboratorio pedidoLaboratorio = evolucion.getPedidosLaboratorio().get(2L);
        assertEquals("Analisis de orina", pedidoLaboratorio.getTexto());
        assertEquals(medico, pedidoLaboratorio.getMedico());
    }

    @Test
    void agregarUnaRecetaDigitalAEvolucion() {
        Medicamento medicamento = new Medicamento(101, "Paracetamol", "Tableta");
        List<Medicamento> medicamentos = List.of(medicamento);

        evolucion.crearRecetaDigital(medicamentos, medico);

        assertEquals(1, evolucion.getRecetasDigitales().size());
        assertTrue(evolucion.getRecetasDigitales().containsKey(1L));

        RecetaDigital receta = evolucion.getRecetasDigitales().get(1L);
        assertNotNull(receta);
    }

    @Test
    void agregarMultiplesRecetasDigitalAEvolucion() {
        Medicamento medicamento1 = new Medicamento(101, "Paracetamol", "Tableta");
        Medicamento medicamento2 = new Medicamento(102, "Ibuprofeno", "Capsula");

        evolucion.crearRecetaDigital(List.of(medicamento1), medico);
        evolucion.crearRecetaDigital(List.of(medicamento2), medico);

        assertEquals(2, evolucion.getRecetasDigitales().size());
        assertTrue(evolucion.getRecetasDigitales().containsKey(1L));
        assertTrue(evolucion.getRecetasDigitales().containsKey(2L));
    }
}