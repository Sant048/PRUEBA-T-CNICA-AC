package com.Prueba.Accenture.product.interfaces;

import com.Prueba.Accenture.product.application.ProductService;
import com.Prueba.Accenture.product.domain.Product;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    // =========================
    // CREATE
    // =========================
    @PostMapping
    public Mono<Product> create(@RequestBody ProductRequest request) {
        return service.create(
                request.getName(),
                request.getStock(),
                request.getBranchId()
        );
    }

    // =========================
    // GET
    // =========================
    @GetMapping("/{id}")
    public Mono<Product> getById(@PathVariable Long id) {
        return service.findById(id);
    }

    @GetMapping
    public Flux<Product> getAll() {
        return service.findAll();
    }

    @GetMapping("/branch/{branchId}")
    public Flux<Product> getByBranch(@PathVariable Long branchId) {
        return service.findByBranch(branchId);
    }

    // =========================
    // UPDATE NAME
    // =========================
    @PutMapping("/{id}")
    public Mono<Product> updateName(
            @PathVariable Long id,
            @RequestBody ProductRequest request
    ) {
        return service.updateName(id, request.getName());
    }

    // =========================
    // UPDATE STOCK
    // =========================
    @PutMapping("/{id}/stock")
    public Mono<Product> updateStock(
            @PathVariable Long id,
            @RequestBody ProductRequest request
    ) {
        return service.updateStock(id, request.getStock());
    }

    // =========================
    // CHANGE BRANCH
    // =========================
    @PutMapping("/{id}/branch")
    public Mono<Product> changeBranch(
            @PathVariable Long id,
            @RequestBody ProductRequest request
    ) {
        return service.changeBranch(id, request.getBranchId());
    }

    // =========================
    // DELETE
    // =========================
    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable Long id) {
        return service.delete(id);
    }
}