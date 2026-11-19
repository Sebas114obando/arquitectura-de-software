package com.ejemplo.catalogo.adapter.in.web.controller;

import com.ejemplo.catalogo.adapter.in.web.dto.PedidoRequest;
import com.ejemplo.catalogo.adapter.in.web.dto.PedidoResponse;
import com.ejemplo.catalogo.domain.model.Pedido;
import com.ejemplo.catalogo.domain.port.in.ConsultarPedidoUseCase;
import com.ejemplo.catalogo.domain.port.in.CrearPedidoUseCase;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private final CrearPedidoUseCase crearPedidoUseCase;
    private final ConsultarPedidoUseCase consultarPedidoUseCase;

    public PedidoController(CrearPedidoUseCase crearPedidoUseCase,
                            ConsultarPedidoUseCase consultarPedidoUseCase) {
        this.crearPedidoUseCase = crearPedidoUseCase;
        this.consultarPedidoUseCase = consultarPedidoUseCase;
    }

    @PostMapping
    public ResponseEntity<PedidoResponse> crearPedido(@RequestBody PedidoRequest request) {
        Pedido pedido = crearPedidoUseCase.crearPedido(
                request.getClienteId(),
                request.getProductoId(),
                request.getCantidad()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(pedido));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponse> consultarPorId(@PathVariable Long id) {
        return consultarPedidoUseCase.consultarPorId(id)
                .map(p -> ResponseEntity.ok(toResponse(p)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/cliente/{clienteId}/total")
    public ResponseEntity<Map<String, Object>> consultarTotalCliente(@PathVariable String clienteId) {
        long total = consultarPedidoUseCase.consultarTotalCompradoPorCliente(clienteId);
        return ResponseEntity.ok(Map.of(
                "clienteId", clienteId,
                "totalComprado", total
        ));
    }

    private static PedidoResponse toResponse(Pedido pedido) {
        return new PedidoResponse(
                pedido.getId(),
                pedido.getClienteId(),
                pedido.getProductoId(),
                pedido.getCantidad(),
                pedido.getEstado().name(),
                pedido.getTotal()
        );
    }
}
