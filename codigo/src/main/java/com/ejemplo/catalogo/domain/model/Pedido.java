package com.ejemplo.catalogo.domain.model;

/**
 * Entidad de dominio Pedido.
 * Modelo puro sin anotaciones de Spring, Jakarta ni persistencia.
 */
public class Pedido {

    private Long id;
    private String clienteId;
    private Long productoId;
    private Integer cantidad;
    private PedidoEstado estado;
    private Long total;

    public Pedido(Long id, String clienteId, Long productoId, Integer cantidad,
                  PedidoEstado estado, Long total) {
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

    public PedidoEstado getEstado() {
        return estado;
    }

    public void setEstado(PedidoEstado estado) {
        this.estado = estado;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }
}
