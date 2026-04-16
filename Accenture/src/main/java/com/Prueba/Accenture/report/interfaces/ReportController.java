package com.Prueba.Accenture.report.interfaces;

import com.Prueba.Accenture.report.application.ReportService;
import com.Prueba.Accenture.report.domain.TopProduct;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/reports")
public class ReportController {

    private final ReportService service;

    public ReportController(ReportService service) {
        this.service = service;
    }

    @GetMapping("/top-products/franchise/{franchiseId}")
    public Flux<TopProduct> getTopProductsByFranchise(@PathVariable Long franchiseId) {
        return service.getTopProductsByFranchise(franchiseId);
    }
}