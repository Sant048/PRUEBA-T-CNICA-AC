package com.Prueba.Accenture.branch.domain;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BranchRepository {

    Mono<Branch> save(Branch branch);

    Mono<Branch> findById(Long id);

    Flux<Branch> findAll();

    Flux<Branch> findByFranchiseId(Long franchiseId);

    Mono<Void> deleteById(Long id);
}