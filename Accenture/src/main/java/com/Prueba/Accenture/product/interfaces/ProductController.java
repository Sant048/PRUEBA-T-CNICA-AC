package com.Prueba.Accenture.product.interfaces;

import com.Prueba.Accenture.product.application.ProductService;
import com.Prueba.Accenture.product.domain.Product;
import com.Prueba.Accenture.product.interfaces.dto.ProductDtoBr;
import com.Prueba.Accenture.product.interfaces.dto.ProductDtoName;
import com.Prueba.Accenture.product.interfaces.dto.ProductDtoStock;
import com.Prueba.Accenture.product.interfaces.dto.ProductRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
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

    // CREATE
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Product> create(@Valid @RequestBody ProductRequest request) {
        return service.create(
                request.getName(),
                request.getStock(),
                request.getBranchId()
        );
    }

    // GET
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

    // UPDATE NAME
    @PutMapping("/{id}")
    public Mono<Product> updateName(@PathVariable Long id,
                                    @Valid @RequestBody ProductDtoName request) {
        return service.updateName(id, request.getName());
    }

    // UPDATE STOCK
    @PutMapping("/{id}/stock")
    public Mono<Product> updateStock(@PathVariable Long id,
                                     @Valid @RequestBody ProductDtoStock request) {
        return service.updateStock(id, request.getStock());
    }

    // CHANGE BRANCH
    @PutMapping("/{id}/branch")
    public Mono<Product> changeBranch(@PathVariable Long id,
                                      @Valid @RequestBody ProductDtoBr request) {
        return service.changeBranch(id, request.getBranchId());
    }

    // DELETE
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> delete(@PathVariable Long id) {
        return service.delete(id);
    }
}