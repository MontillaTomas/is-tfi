package com.example.is_tfi.dominio;

import java.time.LocalDate;

public class Paciente extends Persona {
    private ObraSocial obraSocial;
    private int numeroAfiliadoObraSocial;
    private HistoriaClinica historiaClinica;

    public Paciente(Long dni,
                    Long cuil,
                    String nombre,
                    LocalDate fechaNacimiento,
                    String email,
                    String telefono,
                    ObraSocial obraSocial,
                    int numeroAfiliadoObraSocial,
                    Direccion direccion) {
        super(dni, cuil, nombre, fechaNacimiento, email, telefono, direccion);
        this.obraSocial = obraSocial;
        this.numeroAfiliadoObraSocial = numeroAfiliadoObraSocial;
        this.historiaClinica = new HistoriaClinica();
    }

    public Long getDni(){
        return super.getDni();
    }



    public void agregarDiagnostico(String diagnostico) {
        // La responsabilidad de validar el diagnóstico se la da al servicio
        historiaClinica.agregarDiagnostico(diagnostico);
    }

    public void agregarEvolucion(String diagnostico, String informe, Medico medico) {
        historiaClinica.agregarEvolucion(diagnostico, informe, medico);
    }
}
