package com.Prueba.Accenture.productTest;

import com.Prueba.Accenture.product.application.ProductService;
import com.Prueba.Accenture.product.domain.Product;
import com.Prueba.Accenture.product.domain.ProductRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

class ProductServiceTest {

    private final ProductRepository repository = Mockito.mock(ProductRepository.class);
    private final ProductService service = new ProductService(repository);

    @Test
    void shouldCreateProduct() {

        Product saved = new Product(1L, "Laptop", 10, 1L);

        Mockito.when(repository.save(Mockito.any()))
                .thenReturn(Mono.just(saved));

        StepVerifier.create(service.create("Laptop", 10, 1L))
                .expectNextMatches(p ->
                        p.getName().equals("Laptop") &&
                                p.getStock() == 10 &&
                                p.getBranchId().equals(1L)
                )
                .verifyComplete();
    }

    @Test
    void shouldUpdateStock() {

        Product existing = new Product(1L, "Mouse", 5, 1L);
        Product updated = new Product(1L, "Mouse", 20, 1L);

        Mockito.when(repository.findById(1L))
                .thenReturn(Mono.just(existing));

        Mockito.when(repository.save(Mockito.any()))
                .thenReturn(Mono.just(updated));

        StepVerifier.create(service.updateStock(1L, 20))
                .expectNextMatches(p -> p.getStock() == 20)
                .verifyComplete();
    }

    @Test
    void shouldFailWhenProductNotFound() {

        Mockito.when(repository.findById(1L))
                .thenReturn(Mono.empty());

        StepVerifier.create(service.updateStock(1L, 10))
                .expectError(RuntimeException.class)
                .verify();
    }
}