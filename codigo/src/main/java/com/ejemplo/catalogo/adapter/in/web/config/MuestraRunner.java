package com.ejemplo.catalogo.adapter.in.web.config;

import com.ejemplo.catalogo.domain.model.Pedido;
import com.ejemplo.catalogo.domain.port.in.CrearPedidoUseCase;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Ejecutor de las muestras oficiales de Hoja 1 y Hoja 2.
 * Recorre la arquitectura por su entrada real (el caso de uso CrearPedidoUseCase).
 */
@Component
@Order(10)
public class MuestraRunner implements ApplicationRunner {

    private final CrearPedidoUseCase crearPedidoUseCase;

    public MuestraRunner(CrearPedidoUseCase crearPedidoUseCase) {
        this.crearPedidoUseCase = crearPedidoUseCase;
    }

    @Override
    public void run(ApplicationArguments args) {
        boolean hoja1 = args.containsOption("muestra") &&
                ("hoja-1".equalsIgnoreCase(args.getOptionValues("muestra").get(0))
                        || "1".equalsIgnoreCase(args.getOptionValues("muestra").get(0)));

        boolean hoja2 = args.containsOption("muestra") &&
                ("hoja-2".equalsIgnoreCase(args.getOptionValues("muestra").get(0))
                        || "2".equalsIgnoreCase(args.getOptionValues("muestra").get(0)));

        if (hoja1) {
            ejecutarMuestraHoja1();
            System.exit(0);
        } else if (hoja2) {
            ejecutarMuestraHoja2();
            System.exit(0);
        }
    }

    public void ejecutarMuestraHoja1() {
        // Fila 3: u = 3, stock = 24
        // Línea 1: C-001, u - 1 = 2
        Pedido p1 = crearPedidoUseCase.crearPedido("C-001", 1L, 2);
        imprimir(p1);

        // Línea 2: C-002, u = 3
        Pedido p2 = crearPedidoUseCase.crearPedido("C-002", 1L, 3);
        imprimir(p2);

        // Línea 3: C-001, stock - 2u + 2 = 24 - 6 + 2 = 20
        Pedido p3 = crearPedidoUseCase.crearPedido("C-001", 1L, 20);
        imprimir(p3);

        // Línea 4: C-003, stock - 2u + 1 = 24 - 6 + 1 = 19
        Pedido p4 = crearPedidoUseCase.crearPedido("C-003", 1L, 19);
        imprimir(p4);
    }

    public void ejecutarMuestraHoja2() {
        // Hoja 2 (Q1) - Fila 3: u = 3
        // Línea 1: C-001, 3u = 9
        Pedido p1 = crearPedidoUseCase.crearPedido("C-001", 1L, 9);
        imprimir(p1);

        // Línea 2: C-002, 3u - 1 = 8
        Pedido p2 = crearPedidoUseCase.crearPedido("C-002", 1L, 8);
        imprimir(p2);

        // Línea 3: C-003, 1
        Pedido p3 = crearPedidoUseCase.crearPedido("C-003", 1L, 1);
        imprimir(p3);
    }

    private static void imprimir(Pedido p) {
        System.out.println("PEDIDO " + p.getId() + " " + p.getEstado() + " total=" + p.getTotal());
    }
}
