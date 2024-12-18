package com.example.is_tfi.dominio;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Evolucion {

    private String informe;
    private Medico medico;
    private Map<Long, RecetaDigital> recetasDigitales;
    private Map<Long, PedidoLaboratorio> pedidosLaboratorio;
    private LocalDateTime fechaHora;

    public Evolucion(String informe, Medico medico) {
        this.informe = informe;
        this.medico = medico;
        this.recetasDigitales = new HashMap<>();
        this.pedidosLaboratorio = new HashMap<>();
        this.fechaHora = LocalDateTime.now();
    }

    public Evolucion(String informe, Medico medico, LocalDateTime fechaHora) {
        this.informe = informe;
        this.medico = medico;
        this.recetasDigitales = new HashMap<>();
        this.pedidosLaboratorio = new HashMap<>();
        this.fechaHora = fechaHora;
    }

    public Map<Long, PedidoLaboratorio> getPedidosLaboratorio() {
        return pedidosLaboratorio;
    }

    public Map<Long, RecetaDigital> getRecetasDigitales() {
        return recetasDigitales;
    }

    public String getInforme() {
        return informe;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setRecetasDigitales(Map<Long, RecetaDigital> recetasDigitales) {
        this.recetasDigitales = recetasDigitales;
    }

    public void setPedidosLaboratorio(Map<Long, PedidoLaboratorio> pedidosLaboratorio) {
        this.pedidosLaboratorio = pedidosLaboratorio;
    }

    public void crearRecetaDigital(List<Medicamento> medicamentos, Medico medico) {
        recetasDigitales.put(recetasDigitales.size() + 1L, new RecetaDigital(medicamentos, medico));
    }

    public void crearPedidoLaboratorio(String texto, Medico medico) {
        pedidosLaboratorio.put(pedidosLaboratorio.size() + 1L, new PedidoLaboratorio(texto, medico));
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }
}
