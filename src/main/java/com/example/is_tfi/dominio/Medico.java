package com.example.is_tfi.dominio;

import java.time.LocalDate;

public class Medico extends Persona {
    private int matricula;
    private String especialidad;

    public Medico(Long dni,
                  Long cuil,
                  String nombre,
                  LocalDate fecheNacimiento,
                  String email,
                  String telefono,
                  Direccion direccion,
                  int matricula,
                  String especialidad) {
        super(dni, cuil, nombre, fecheNacimiento, email, telefono, direccion);
        this.matricula = matricula;
        this.especialidad = especialidad;
    }

    public int getMatricula() {
        return matricula;
    }

    public String getEspecialidad() {
        return especialidad;
    }
}
