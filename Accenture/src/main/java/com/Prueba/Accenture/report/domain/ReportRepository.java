package com.Prueba.Accenture.report.domain;

import reactor.core.publisher.Flux;

public interface ReportRepository {

    Flux<TopProduct> findTopProductsByBranch();
}