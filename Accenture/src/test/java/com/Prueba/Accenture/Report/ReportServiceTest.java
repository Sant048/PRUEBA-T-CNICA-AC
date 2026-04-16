package com.Prueba.Accenture.Report;

import com.Prueba.Accenture.report.application.ReportService;
import com.Prueba.Accenture.report.domain.ReportRepository;
import com.Prueba.Accenture.report.domain.TopProduct;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

class ReportServiceTest {

    private final ReportRepository repository = Mockito.mock(ReportRepository.class);
    private final ReportService service = new ReportService(repository);

    @Test
    void shouldReturnTopProductsByFranchise() {

        Long franchiseId = 1L;

        Mockito.when(repository.findTopProductsByFranchise(franchiseId))
                .thenReturn(Flux.just(
                        new TopProduct(1L, 10L, "Laptop", 50),
                        new TopProduct(2L, 20L, "Mouse", 30)
                ));

        StepVerifier.create(service.getTopProductsByFranchise(franchiseId))
                .expectNextMatches(p -> p.getBranchId().equals(1L) && p.getStock() == 50)
                .expectNextMatches(p -> p.getBranchId().equals(2L) && p.getStock() == 30)
                .verifyComplete();
    }
}