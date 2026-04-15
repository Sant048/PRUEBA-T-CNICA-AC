package com.Prueba.Accenture.Report;

import com.Prueba.Accenture.report.application.ReportService;
import com.Prueba.Accenture.report.domain.TopProduct;
import com.Prueba.Accenture.report.interfaces.ReportController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

class ReportControllerTest {

    private WebTestClient client;
    private ReportService service;

    @BeforeEach
    void setup() {
        service = Mockito.mock(ReportService.class);
        ReportController controller = new ReportController(service);

        client = WebTestClient
                .bindToController(controller)
                .build();
    }

    @Test
    void shouldReturnTopProducts() {

        Mockito.when(service.getTopProductsByBranch())
                .thenReturn(Flux.just(
                        new TopProduct(1L, 1L, "Laptop", 100)
                ));

        client.get()
                .uri("/reports/top-products")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$[0].productName").isEqualTo("Laptop")
                .jsonPath("$[0].stock").isEqualTo(100);
    }
}