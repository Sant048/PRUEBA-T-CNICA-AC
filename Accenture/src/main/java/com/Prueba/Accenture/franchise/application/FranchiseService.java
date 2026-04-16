package com.Prueba.Accenture.franchise.application;

import com.Prueba.Accenture.franchise.domain.Franchise;
import com.Prueba.Accenture.franchise.domain.FranchiseRepository;
import com.Prueba.Accenture.shared.exception.NotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class FranchiseService {

    private final FranchiseRepository repository;

    public FranchiseService(FranchiseRepository repository) {
        this.repository = repository;
    }

    // =========================
    // CREATE
    // =========================
    public Mono<Franchise> create(String name) {
        Franchise franchise = new Franchise();
        franchise.rename(name);
        return repository.save(franchise);
    }

    // =========================
    // FIND BY ID (FIX CLAVE)
    // =========================
    public Mono<Franchise> findById(Long id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Franchise not found")));
    }

    // =========================
    // FIND ALL
    // =========================
    public Flux<Franchise> findAll() {
        return repository.findAll();
    }

    // =========================
    // UPDATE
    // =========================
    public Mono<Franchise> updateName(Long id, String newName) {
        return findById(id)
                .flatMap(franchise ->
                        repository.save(
                                new Franchise(franchise.getId(), newName)
                        )
                );
    }

    // =========================
    // DELETE (FIX IMPORTANTE)
    // =========================
// DELETE (MEJORADO)
    public Mono<Void> delete(Long id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Franchise not found")))
                .flatMap(f -> repository.deleteById(id));
    }
}