package com.ejemplo.catalogo.adapter.in.web.dto;

public class PedidoRequest {

    private String clienteId;
    private Long productoId;
    private Integer cantidad;

    public PedidoRequest() {
    }

    public PedidoRequest(String clienteId, Long productoId, Integer cantidad) {
        this.clienteId = clienteId;
        this.productoId = productoId;
        this.cantidad = cantidad;
    }

    public String getClienteId() {
        return clienteId;
    }

    public void setClienteId(String clienteId) {
        this.clienteId = clienteId;
    }

    public Long getProductoId() {
        return productoId;
    }

    public void setProductoId(Long productoId) {
        this.productoId = productoId;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }
}
