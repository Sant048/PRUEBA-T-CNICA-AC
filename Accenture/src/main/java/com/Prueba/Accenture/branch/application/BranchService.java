package com.Prueba.Accenture.branch.application;

import com.Prueba.Accenture.branch.domain.Branch;
import com.Prueba.Accenture.branch.domain.BranchRepository;
import com.Prueba.Accenture.shared.exception.NotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class BranchService {

    private final BranchRepository repository;

    public BranchService(BranchRepository repository) {
        this.repository = repository;
    }

    // =========================
    // CREATE
    // =========================
    public Mono<Branch> create(String name, Long franchiseId) {

        Branch branch = new Branch();
        branch.rename(name);
        branch.assignToFranchise(franchiseId);

        return repository.save(branch);
    }

    // =========================
    // FIND BY ID (FIX CLAVE)
    // =========================
    public Mono<Branch> findById(Long id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Branch not found")));
    }

    // =========================
    // FIND ALL
    // =========================
    public Flux<Branch> findAll() {
        return repository.findAll();
    }

    // =========================
    // FIND BY FRANCHISE
    // =========================
    public Flux<Branch> findByFranchise(Long franchiseId) {
        return repository.findByFranchiseId(franchiseId);
    }

    // =========================
    // UPDATE NAME
    // =========================
    public Mono<Branch> updateName(Long id, String newName) {

        return findById(id)
                .flatMap(branch -> {
                    branch.rename(newName);
                    return repository.save(branch);
                });
    }

    public Mono<Void> delete(Long id) {
        return findById(id)
                .flatMap(branch -> repository.deleteById(branch.getId()));
    }
}