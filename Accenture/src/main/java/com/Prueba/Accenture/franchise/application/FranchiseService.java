package com.Prueba.Accenture.franchise.application;

import com.Prueba.Accenture.franchise.domain.Franchise;
import com.Prueba.Accenture.franchise.domain.FranchiseRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class FranchiseService {

    private final FranchiseRepository repository;

    public FranchiseService(FranchiseRepository repository) {
        this.repository = repository;
    }

    // CREATE
    public Mono<Franchise> create(String name) {
        Franchise franchise = new Franchise(null, name);
        return repository.save(franchise);
    }

    // FIND BY ID
    public Mono<Franchise> findById(Long id) {
        return repository.findById(id);
    }

    // FIND ALL
    public Flux<Franchise> findAll() {
        return repository.findAll();
    }

    // UPDATE
    public Mono<Franchise> updateName(Long id, String newName) {

        return repository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Franchise not found")))
                .flatMap(franchise -> {
                    franchise.rename(newName);
                    return repository.save(franchise);
                });
    }

    // DELETE
    public Mono<Void> delete(Long id) {
        return repository.deleteById(id);
    }
}