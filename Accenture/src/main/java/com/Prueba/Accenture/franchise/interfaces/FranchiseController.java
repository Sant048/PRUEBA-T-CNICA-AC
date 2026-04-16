package com.Prueba.Accenture.franchise.interfaces;

import com.Prueba.Accenture.franchise.application.FranchiseService;
import com.Prueba.Accenture.franchise.domain.Franchise;
import com.Prueba.Accenture.franchise.interfaces.dto.FranchiseRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/franchises")
public class FranchiseController {

    private final FranchiseService service;

    public FranchiseController(FranchiseService service) {
        this.service = service;
    }

    // =========================
    // CREATE
    // =========================
    @PostMapping
    public Mono<Franchise> create(@Valid @RequestBody FranchiseRequest request) {
        return service.create(request.getName());
    }

    // =========================
    // FIND BY ID
    // =========================
    @GetMapping("/{id}")
    public Mono<Franchise> getById(@PathVariable Long id) {
        return service.findById(id);
    }

    // =========================
    // FIND ALL
    // =========================
    @GetMapping
    public Flux<Franchise> getAll() {
        return service.findAll();
    }

    // =========================
    // UPDATE NAME
    // =========================
    @PutMapping("/{id}")
    public Mono<Franchise> updateName(
            @PathVariable Long id,
            @Valid @RequestBody FranchiseRequest request
    ) {
        return service.updateName(id, request.getName());
    }

    // =========================
    // DELETE
    // =========================
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> delete(@PathVariable Long id) {
        return service.delete(id);
    }
}