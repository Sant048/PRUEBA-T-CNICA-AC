package com.Prueba.Accenture.TestFrachise;

import com.Prueba.Accenture.franchise.application.FranchiseService;
import com.Prueba.Accenture.franchise.domain.Franchise;
import com.Prueba.Accenture.franchise.interfaces.FranchiseController;
import com.Prueba.Accenture.franchise.interfaces.dto.FranchiseRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

class FranchiseControllerTest {

    private WebTestClient client;
    private FranchiseService service;

    @BeforeEach
    void setup() {
        service = Mockito.mock(FranchiseService.class);

        FranchiseController controller = new FranchiseController(service);

        client = WebTestClient
                .bindToController(controller)
                .build();
    }

    @Test
    void shouldCreateFranchise() {

        Franchise franchise = new Franchise(1L, "Nike");

        Mockito.when(service.create("Nike"))
                .thenReturn(Mono.just(franchise));

        FranchiseRequest request = new FranchiseRequest();
        client.post()
                .uri("/franchises")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("{\"name\":\"Nike\"}")
                .exchange();
    }

    @Test
    void shouldGetById() {

        Mockito.when(service.findById(1L))
                .thenReturn(Mono.just(new Franchise(1L, "Adidas")));

        client.get()
                .uri("/franchises/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.name").isEqualTo("Adidas");
    }

    @Test
    void shouldGetAll() {

        Mockito.when(service.findAll())
                .thenReturn(Flux.just(
                        new Franchise(1L, "A"),
                        new Franchise(2L, "B")
                ));

        client.get()
                .uri("/franchises")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.length()").isEqualTo(2);
    }

    @Test
    void shouldUpdateFranchise() {

        Mockito.when(service.updateName(1L, "New"))
                .thenReturn(Mono.just(new Franchise(1L, "New")));

        client.put()
                .uri("/franchises/1")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("{\"name\":\"New\"}")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.name").isEqualTo("New");
    }

    @Test
    void shouldDeleteFranchise() {

        Mockito.when(service.delete(1L))
                .thenReturn(Mono.empty());

        client.delete()
                .uri("/franchises/1")
                .exchange()
                .expectStatus().isNoContent();
    }
}