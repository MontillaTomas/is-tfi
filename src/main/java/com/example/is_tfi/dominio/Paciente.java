package com.example.is_tfi.dominio;

import java.time.LocalDate;

public class Paciente {
    private int dni;
    private String nombre;
    private HistoriaClinica historiaClinica;
    private LocalDate fechaNacimiento;

    public int getDni(){
        return dni;
    }

    public Paciente(int dni,
                    String nombre,
                    LocalDate fechaNacimiento) {
        this.dni = dni;
        this.nombre = nombre;
        this.historiaClinica = new HistoriaClinica();
        this.fechaNacimiento = fechaNacimiento;
    }

    public void agregarDiagnostico(String diagnostico) {
        // La responsabilidad de validar el diagnóstico se la da al servicio
        historiaClinica.agregarDiagnostico(diagnostico);
    }

    public void agregarEvolucion(String diagnostico, String informe, Medico medico) {
        historiaClinica.agregarEvolucion(diagnostico, informe, medico);
    }
}
