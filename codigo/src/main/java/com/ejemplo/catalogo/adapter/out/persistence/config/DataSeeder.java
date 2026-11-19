package com.ejemplo.catalogo.adapter.out.persistence.config;

import com.ejemplo.catalogo.adapter.out.persistence.persistence.ProductJpaEntity;
import com.ejemplo.catalogo.adapter.out.persistence.persistence.ProductJpaRepository;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Inicializador de datos para la variante Fila 3:
 * Producto: Monitor
 * Precio: $600.000
 * Stock inicial: 24 unidades
 * Umbral de volumen u: 3
 * Descuento d: 12%
 */
@Component
public class DataSeeder implements CommandLineRunner {

    private final ProductJpaRepository repository;

    public DataSeeder(ProductJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) {
        if (repository.count() > 0) {
            return;
        }

        repository.saveAll(List.of(
                new ProductJpaEntity("Monitor", "Monitor de alta resolución",
                        new BigDecimal("600000.00"), 24, "electronica")
        ));
    }
}
