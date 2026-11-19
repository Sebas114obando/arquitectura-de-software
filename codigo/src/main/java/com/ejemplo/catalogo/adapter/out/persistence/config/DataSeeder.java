package com.ejemplo.catalogo.adapter.out.persistence.config;

import com.ejemplo.catalogo.adapter.out.persistence.persistence.ProductJpaEntity;
import com.ejemplo.catalogo.adapter.out.persistence.persistence.ProductJpaRepository;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

    private final ProductJpaRepository repository;

    public DataSeeder(ProductJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) {
        if (repository.count() > 0) {
            return; // data already exists: do not duplicate
        }

        repository.saveAll(List.of(
                new ProductJpaEntity("Laptop Pro 14", "Portátil de 14 pulgadas",
                        new BigDecimal("1299.99"), 8, "electronica"),
                new ProductJpaEntity("Mouse inalámbrico", "Bluetooth, 3 botones",
                        new BigDecimal("24.90"), 40, "electronica"),
                new ProductJpaEntity("Silla ergonómica", "Soporte lumbar",
                        new BigDecimal("219.00"), 5, "hogar"),
                new ProductJpaEntity("Lámpara LED", "Luz cálida regulable",
                        new BigDecimal("35.50"), 18, "hogar"),
                new ProductJpaEntity("Café de especialidad", "Bolsa de 500 g",
                        new BigDecimal("14.75"), 60, "alimentos")
        ));
    }
}

