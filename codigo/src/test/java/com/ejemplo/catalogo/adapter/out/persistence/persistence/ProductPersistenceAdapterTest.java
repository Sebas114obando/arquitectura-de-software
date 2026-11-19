package com.ejemplo.catalogo.adapter.out.persistence.persistence;

import com.ejemplo.catalogo.domain.model.Product;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductPersistenceAdapterTest {

    @Mock
    private ProductJpaRepository jpaRepository;

    @InjectMocks
    private ProductPersistenceAdapter adapter;

    @Test
    void findAllMapsEntitiesToDomainModel() {
        ProductJpaEntity entity = new ProductJpaEntity("Mouse",
                "Inalámbrico", new BigDecimal("24.90"), 40, "electronica");
        entity.setId(1L);
        when(jpaRepository.findAll()).thenReturn(List.of(entity));

        List<Product> result = adapter.findAll();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getId()).isEqualTo(1L);
        assertThat(result.get(0).getName()).isEqualTo("Mouse");
    }

    @Test
    void findByNameContainingIgnoreCaseMapsEntitiesToDomainModel() {
        ProductJpaEntity entity = new ProductJpaEntity("Mouse",
                "Inalámbrico", new BigDecimal("24.90"), 40, "electronica");
        entity.setId(1L);
        when(jpaRepository.findByNameContainingIgnoreCase("mou")).thenReturn(List.of(entity));

        List<Product> result = adapter.findByNameContainingIgnoreCase("mou");

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getId()).isEqualTo(1L);
    }

    @Test
    void findByIdMapsEntityToDomainModel() {
        ProductJpaEntity entity = new ProductJpaEntity("Mouse",
                "Inalámbrico", new BigDecimal("24.90"), 40, "electronica");
        entity.setId(1L);

        when(jpaRepository.findById(1L)).thenReturn(Optional.of(entity));

        Optional<Product> result = adapter.findById(1L);

        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo(1L);
        assertThat(result.get().getName()).isEqualTo("Mouse");
        assertThat(result.get().getPrice()).isEqualByComparingTo("24.90");
    }

    @Test
    void saveMapsDomainToEntityAndBack() {
        Product domain = new Product("Teclado", "desc", new BigDecimal("50.00"), 5, "electronica");
        ProductJpaEntity savedEntity = new ProductJpaEntity("Teclado", "desc", new BigDecimal("50.00"), 5, "electronica");
        savedEntity.setId(10L);

        when(jpaRepository.save(any(ProductJpaEntity.class))).thenReturn(savedEntity);

        Product result = adapter.save(domain);

        assertThat(result.getId()).isEqualTo(10L);
        assertThat(result.getName()).isEqualTo("Teclado");
    }

    @Test
    void deleteDelegatesToJpaRepository() {
        Product domain = new Product("Teclado", "desc", new BigDecimal("50.00"), 5, "electronica");
        domain.setId(7L);

        adapter.delete(domain);

        verify(jpaRepository).deleteById(7L);
    }
}

