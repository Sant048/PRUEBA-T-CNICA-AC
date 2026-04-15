package com.Prueba.Accenture.productTest;

import com.Prueba.Accenture.product.application.ProductService;
import com.Prueba.Accenture.product.domain.Product;
import com.Prueba.Accenture.product.interfaces.ProductController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

class ProductControllerTest {

    private WebTestClient client;
    private ProductService service;

    @BeforeEach
    void setup() {
        service = Mockito.mock(ProductService.class);
        ProductController controller = new ProductController(service);

        client = WebTestClient
                .bindToController(controller)
                .build();
    }

    @Test
    void shouldCreateProduct() {

        Product product = new Product(1L, "Laptop", 10, 1L);

        Mockito.when(service.create("Laptop", 10, 1L))
                .thenReturn(Mono.just(product));

        client.post()
                .uri("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("""
                        {
                          "name": "Laptop",
                          "stock": 10,
                          "branchId": 1
                        }
                        """)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.name").isEqualTo("Laptop");
    }

    @Test
    void shouldGetById() {

        Mockito.when(service.findById(1L))
                .thenReturn(Mono.just(new Product(1L, "Mouse", 5, 1L)));

        client.get()
                .uri("/products/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.name").isEqualTo("Mouse");
    }

    @Test
    void shouldGetAll() {

        Mockito.when(service.findAll())
                .thenReturn(Flux.just(
                        new Product(1L, "A", 1, 1L),
                        new Product(2L, "B", 2, 1L)
                ));

        client.get()
                .uri("/products")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.length()").isEqualTo(2);
    }

    @Test
    void shouldUpdateStock() {

        Product updated = new Product(1L, "Laptop", 50, 1L);

        Mockito.when(service.updateStock(1L, 50))
                .thenReturn(Mono.just(updated));

        client.put()
                .uri("/products/1/stock")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("""
                        {
                          "stock": 50
                        }
                        """)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.stock").isEqualTo(50);
    }

    @Test
    void shouldDeleteProduct() {

        Mockito.when(service.delete(1L))
                .thenReturn(Mono.empty());

        client.delete()
                .uri("/products/1")
                .exchange()
                .expectStatus().isOk();
    }
}