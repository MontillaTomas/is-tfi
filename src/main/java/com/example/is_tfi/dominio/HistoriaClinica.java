package com.example.is_tfi.dominio;

import java.util.ArrayList;
import java.util.List;

public class HistoriaClinica {
    private List<Diagnostico> diagnosticos;
    public HistoriaClinica() {
        diagnosticos = new ArrayList<>();
    }

    public void agregarDiagnostico(String diagnostico) {
        diagnosticos.add(new Diagnostico(diagnostico));
    }

    private Diagnostico buscarDiagnostico(String nombreDiagnostico) {
        return diagnosticos.stream()
                .filter(diagnostico -> diagnostico.getNombre().equals(nombreDiagnostico))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Diagnostico no encontrado"));
    }

    public void agregarEvolucion(String nombreDiagnostico, String informe, Medico medico) {
        Diagnostico diagnostico = buscarDiagnostico(nombreDiagnostico);
        diagnostico.agregarEvolucion(informe, medico);
    }

    public void crearRecetaDigital(String diagnostico, Long idEvolucion, List<Medicamento> medicamentos, Medico medico) {
        Diagnostico d = buscarDiagnostico(diagnostico);
        d.crearRecetaDigital(idEvolucion, medicamentos, medico);
    }

    public void crearPedidoLaboratorio(String diagnostico, Long idEvolucion, String texto, Medico medico) {
        Diagnostico d = buscarDiagnostico(diagnostico);
        d.crearPedidoLaboratorio(idEvolucion, texto, medico);
    }
}
