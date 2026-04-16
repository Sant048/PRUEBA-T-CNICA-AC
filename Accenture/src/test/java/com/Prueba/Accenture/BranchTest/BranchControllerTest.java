package com.Prueba.Accenture.BranchTest;

import com.Prueba.Accenture.branch.application.BranchService;
import com.Prueba.Accenture.branch.domain.Branch;
import com.Prueba.Accenture.branch.interfaces.BranchController;
import com.Prueba.Accenture.branch.interfaces.dto.BranchRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

class BranchControllerTest {

    private WebTestClient client;
    private BranchService service;

    @BeforeEach
    void setup() {
        service = Mockito.mock(BranchService.class);
        BranchController controller = new BranchController(service);

        client = WebTestClient
                .bindToController(controller)
                .build();
    }

    @Test
    void shouldCreateBranch() {

        Mockito.when(service.create("Branch A", 1L))
                .thenReturn(Mono.just(new Branch(1L, "Branch A", 1L)));

        BranchRequest request = new BranchRequest("Branch A", 1L);

        client.post()
                .uri("/branches")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk()   // ✅ cambiado
                .expectBody()
                .jsonPath("$.name").isEqualTo("Branch A");
    }

    @Test
    void shouldGetById() {

        Mockito.when(service.findById(1L))
                .thenReturn(Mono.just(new Branch(1L, "A", 1L)));

        client.get()
                .uri("/branches/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.name").isEqualTo("A");
    }

    @Test
    void shouldGetAll() {

        Mockito.when(service.findAll())
                .thenReturn(Flux.just(
                        new Branch(1L, "A", 1L),
                        new Branch(2L, "B", 1L)
                ));

        client.get()
                .uri("/branches")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.length()").isEqualTo(2);
    }

    @Test
    void shouldGetByFranchise() {

        Mockito.when(service.findByFranchise(1L))
                .thenReturn(Flux.just(new Branch(1L, "A", 1L)));

        client.get()
                .uri("/branches?franchiseId=1")  // ✅ corregido
                .exchange()
                .expectStatus().isOk()           // ✅ corregido
                .expectBody()
                .jsonPath("$.length()").isEqualTo(1);
    }

    @Test
    void shouldUpdateName() {

        Mockito.when(service.updateName(1L, "New"))
                .thenReturn(Mono.just(new Branch(1L, "New", 1L)));

        BranchRequest request = new BranchRequest("New", 1L);

        client.put()
                .uri("/branches/1")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.name").isEqualTo("New");
    }

    @Test
    void shouldChangeFranchise() {

        Mockito.when(service.changeFranchise(1L, 99L))
                .thenReturn(Mono.just(new Branch(1L, "A", 99L)));

        BranchRequest request = new BranchRequest("A", 99L);

        client.put()
                .uri("/branches/1/franchise")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.franchiseId").isEqualTo(99);
    }

    @Test
    void shouldDelete() {

        Mockito.when(service.delete(1L))
                .thenReturn(Mono.empty());

        client.delete()
                .uri("/branches/1")
                .exchange()
                .expectStatus().isOk(); // 🔥 ahora coincide con tu controller
    }
}