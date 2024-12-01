package com.example.is_tfi.dominio;

public class ObraSocial {
    private int codigo;
    private String denominacion;
    private String sigla;

    public ObraSocial(int codigo, String denominacion, String sigla) {
        this.codigo = codigo;
        this.denominacion = denominacion;
        this.sigla = sigla;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getDenominacion() {
        return denominacion;
    }

    public String getSigla() {
        return sigla;
    }
}
