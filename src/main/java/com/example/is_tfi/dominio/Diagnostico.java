package com.example.is_tfi.dominio;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Diagnostico {
    private String nombre;
    private Map<Long, Evolucion> evoluciones;

    public String getNombre() {
        return nombre;
    }

    public Map<Long, Evolucion> getEvoluciones() {
        return evoluciones;
    }

    public Diagnostico(String nombre) {
        this.nombre = nombre;
        this.evoluciones = new HashMap<>();
    }

    public void agregarEvolucion(String informe, Medico medico) {
        evoluciones.put(evoluciones.size() + 1L, new Evolucion(informe, medico));
    }

    public void crearRecetaDigital(Long idEvolucion, List<Medicamento> medicamentos, Medico medico) {
        if (!evoluciones.containsKey(idEvolucion)) {
            throw new RuntimeException("No se encontró la evolución con el id " + idEvolucion);
        }

        evoluciones.get(idEvolucion).crearRecetaDigital(medicamentos, medico);
    }

    public void crearPedidoLaboratorio(Long idEvolucion, String texto, Medico medico) {
        if (!evoluciones.containsKey(idEvolucion)) {
            throw new RuntimeException("No se encontró la evolución con el id " + idEvolucion);
        }

        evoluciones.get(idEvolucion).crearPedidoLaboratorio(texto, medico);
    }
}
