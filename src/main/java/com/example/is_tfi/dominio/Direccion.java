package com.example.is_tfi.dominio;

public class Direccion {
    private String calle;
    private int numero;
    private int piso;
    private String departamento;
    private String codigoPostal;
    private String ciudad;


    public Direccion(String calle, int numero, String codigoPostal, String ciudad) {
        this.calle = calle;
        this.numero = numero;
        this.codigoPostal = codigoPostal;
        this.ciudad = ciudad;
    }

    public Direccion(String calle, int numero, int piso, String departamento, String codigoPostal, String ciudad) {
        this.calle = calle;
        this.numero = numero;
        this.piso = piso;
        this.departamento = departamento;
        this.codigoPostal = codigoPostal;
        this.ciudad = ciudad;
    }
}
