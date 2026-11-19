package com.ejemplo.catalogo.adapter.in.web.dto;

public class PedidoResponse {

    private Long id;
    private String clienteId;
    private Long productoId;
    private Integer cantidad;
    private String estado;
    private Long total;

    public PedidoResponse() {
    }

    public PedidoResponse(Long id, String clienteId, Long productoId,
                          Integer cantidad, String estado, Long total) {
        this.id = id;
        this.clienteId = clienteId;
        this.productoId = productoId;
        this.cantidad = cantidad;
        this.estado = estado;
        this.total = total;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }
}
