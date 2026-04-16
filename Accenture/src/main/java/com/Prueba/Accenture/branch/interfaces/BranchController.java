package com.Prueba.Accenture.branch.interfaces;

import com.Prueba.Accenture.branch.application.BranchService;
import com.Prueba.Accenture.branch.domain.Branch;
import com.Prueba.Accenture.branch.interfaces.dto.BranchRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
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
    public Mono<Branch> create(@Valid @RequestBody BranchRequest request) {
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

    @GetMapping
    public Flux<Branch> getAll(@RequestParam(required = false) Long franchiseId) {
        if (franchiseId != null) {
            return service.findByFranchise(franchiseId);
        }
        return service.findAll();
    }

    // =========================
    // UPDATE NAME
    // =========================
    @PutMapping("/{id}")
    public Mono<Branch> updateName(
            @PathVariable Long id,
            @Valid @RequestBody BranchRequest request
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