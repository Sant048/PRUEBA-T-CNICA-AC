package com.Prueba.Accenture.report.application;

import com.Prueba.Accenture.report.domain.ReportRepository;
import com.Prueba.Accenture.report.domain.TopProduct;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class ReportService {

    private final ReportRepository repository;

    public ReportService(ReportRepository repository) {
        this.repository = repository;
    }

    public Flux<TopProduct> getTopProductsByBranch() {
        return repository.findTopProductsByBranch();
    }
}