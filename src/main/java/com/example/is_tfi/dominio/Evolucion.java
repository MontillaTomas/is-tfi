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

    public void crearRecetaDigital(List<Medicamento> medicamentos, Medico medico) {
        recetasDigitales.put(recetasDigitales.size() + 1L, new RecetaDigital(medicamentos, medico));
    }

    public void crearPedidoLaboratorio(String texto, Medico medico) {
        pedidosLaboratorio.put(pedidosLaboratorio.size() + 1L, new PedidoLaboratorio(texto, medico));
    }
}
