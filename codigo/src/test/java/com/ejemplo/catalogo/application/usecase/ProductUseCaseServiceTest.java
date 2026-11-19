package com.ejemplo.catalogo.application.usecase;

import com.ejemplo.catalogo.domain.exception.ProductNotFoundException;
import com.ejemplo.catalogo.domain.model.Product;
import com.ejemplo.catalogo.domain.port.out.ProductRepository;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductUseCaseServiceTest {

    @Mock
    private ProductRepository repository; // fake repository

    @InjectMocks
    private ProductUseCaseService service; // real use case

    @Test
    void findAllReturnsAllWhenQueryIsBlank() {
        Product p = new Product("Teclado", "desc", new BigDecimal("10.00"), 5, "electronica");
        when(repository.findAll()).thenReturn(List.of(p));

        List<Product> result = service.findAll(null);

        assertThat(result).containsExactly(p);
    }

    @Test
    void findAllFiltersByNameWhenQueryIsPresent() {
        Product p = new Product("Laptop", "desc", new BigDecimal("100.00"), 2, "electronica");
        when(repository.findByNameContainingIgnoreCase("lap")).thenReturn(List.of(p));

        List<Product> result = service.findAll("lap");

        assertThat(result).containsExactly(p);
    }

    @Test
    void findByIdThrowsWhenProductDoesNotExist() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.findById(99L))
                .isInstanceOf(ProductNotFoundException.class)
                .hasMessageContaining("99");
    }

    @Test
    void createSavesProduct() {
        Product product = new Product("Mouse", "desc", new BigDecimal("20.00"), 10, "electronica");
        when(repository.save(product)).thenReturn(product);

        Product result = service.create(product);

        assertThat(result).isEqualTo(product);
    }

    @Test
    void updateCopiesFieldsOntoTheExistingProduct() {
        Product existing = new Product("Viejo",
                "desc", new BigDecimal("1.00"), 1, "hogar");
        existing.setId(2L);
        Product changes = new Product("Nuevo",
                "desc nueva", new BigDecimal("9.99"), 5, "electronica");

        when(repository.findById(2L)).thenReturn(Optional.of(existing));
        when(repository.save(any(Product.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Product result = service.update(2L, changes);

        assertThat(result.getName()).isEqualTo("Nuevo");
        assertThat(result.getPrice()).isEqualByComparingTo("9.99");
        assertThat(result.getId()).isEqualTo(2L);
    }
}

