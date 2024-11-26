package com.example.is_tfi.dominio;

public class PedidoLaboratorio {
    private String texto;
    private Medico medico;

    public PedidoLaboratorio(String texto, Medico medico) {
        if(texto.isEmpty()) throw new IllegalArgumentException("El texto del pedido no puede estar vacío");

        this.texto = texto;
        this.medico = medico;
    }
}
