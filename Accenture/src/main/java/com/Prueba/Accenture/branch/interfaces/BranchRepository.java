package com.Prueba.Accenture.branch.interfaces;

import com.Prueba.Accenture.branch.domain.Branch;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BranchRepository {

    Mono<Branch> save(Branch branch);

    Mono<Branch> findById(Long id);

    Flux<Branch> findByFranchiseId(Long franchiseId);

    Mono<Void> deleteById(Long id);
}