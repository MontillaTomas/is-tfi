package com.example.is_tfi.dominio;

public class Medicamento {
   private int codigo;
   private String descripcion;
   private String formato;

    public Medicamento(int codigo, String descripcion, String formato) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.formato = formato;
    }
}
