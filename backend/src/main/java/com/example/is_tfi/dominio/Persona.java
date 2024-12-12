package com.example.is_tfi.dominio;

import java.time.LocalDate;

public class Persona {
    private Long dni;
    private Long cuil;
    private String nombre;
    private LocalDate fechaNacimiento;
    private String email;
    private String telefono;
    private Direccion direccion;

    public Persona(Long dni, Long cuil, String nombre, LocalDate fechaNacimiento, String email, String telefono, Direccion direccion) {
        this.dni = dni;
        this.cuil = cuil;
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
        this.email = email;
        this.telefono = telefono;
        this.direccion = direccion;
    }

    public Long getDni() {
        return dni;
    }

    public Long getCuil() {
        return cuil;
    }

    public String getNombre() {
        return nombre;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefono() {
        return telefono;
    }

    public Direccion getDireccion() {
        return direccion;
    }
}
