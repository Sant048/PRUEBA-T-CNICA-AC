package com.Prueba.Accenture.branch.interfaces;

import com.Prueba.Accenture.branch.application.BranchService;
import com.Prueba.Accenture.branch.domain.Branch;
import com.Prueba.Accenture.branch.interfaces.dto.BranchRequest;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/branches")
public class BranchController {

    private final BranchService service;

    public BranchController(BranchService service) {
        this.service = service;
    }

    // =========================
    // CREATE
    // =========================
    @PostMapping
    public Mono<Branch> create(@RequestBody BranchRequest request) {
        return service.create(
                request.getName(),
                request.getFranchiseId()
        );
    }

    // =========================
    // GET BY ID
    // =========================
    @GetMapping("/{id}")
    public Mono<Branch> getById(@PathVariable Long id) {
        return service.findById(id);
    }

    // =========================
    // GET ALL
    // =========================
    @GetMapping
    public Flux<Branch> getAll() {
        return service.findAll();
    }

    // =========================
    // GET BY FRANCHISE
    // =========================
    @GetMapping("/franchise/{franchiseId}")
    public Flux<Branch> getByFranchise(@PathVariable Long franchiseId) {
        return service.findByFranchise(franchiseId);
    }

    // =========================
    // UPDATE NAME
    // =========================
    @PutMapping("/{id}")
    public Mono<Branch> updateName(
            @PathVariable Long id,
            @RequestBody BranchRequest request
    ) {
        return service.updateName(id, request.getName());
    }

    // =========================
    // CHANGE FRANCHISE
    // =========================
    @PutMapping("/{id}/franchise")
    public Mono<Branch> changeFranchise(
            @PathVariable Long id,
            @RequestBody BranchRequest request
    ) {
        return service.changeFranchise(id, request.getFranchiseId());
    }

    // =========================
    // DELETE
    // =========================
    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable Long id) {
        return service.delete(id);
    }
}