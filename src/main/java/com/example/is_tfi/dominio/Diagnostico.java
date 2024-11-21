package com.example.is_tfi.dominio;

import java.util.ArrayList;
import java.util.List;

public class Diagnostico {
    private String nombre;
    private List<Evolucion> evoluciones;

    public String getNombre() {
        return nombre;
    }

    public Diagnostico(String nombre) {
        this.nombre = nombre;
        this.evoluciones = new ArrayList<>();
    }

    public void agregarEvolucion(String informe, Medico medico) {
        evoluciones.add(new Evolucion(informe, medico));
    }
}
