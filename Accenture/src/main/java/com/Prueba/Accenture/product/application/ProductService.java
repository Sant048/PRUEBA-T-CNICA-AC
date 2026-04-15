package com.Prueba.Accenture.product.application;

import com.Prueba.Accenture.product.domain.Product;
import com.Prueba.Accenture.product.domain.ProductRepository;
import com.Prueba.Accenture.shared.exception.NotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductService {

    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    // =========================
    // CREATE PRODUCT
    // =========================
    public Mono<Product> create(String name, Integer stock, Long branchId) {

        Product product = new Product();
        product.rename(name);
        product.updateStock(stock);
        product.assignToBranch(branchId);

        return repository.save(product);
    }

    // =========================
    // FIND
    // =========================
    public Mono<Product> findById(Long id) {
        return repository.findById(id);
    }

    public Flux<Product> findAll() {
        return repository.findAll();
    }

    public Flux<Product> findByBranch(Long branchId) {
        return repository.findByBranchId(branchId);
    }

    // =========================
    // UPDATE NAME
    // =========================
    public Mono<Product> updateName(Long id, String name) {

        return repository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Product not found")))
                .flatMap(product -> {
                    product.rename(name);
                    return repository.save(product);
                });
    }

    // =========================
    // UPDATE STOCK
    // =========================
    public Mono<Product> updateStock(Long id, Integer stock) {

        return repository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Product not found")))
                .flatMap(product -> {
                    product.updateStock(stock);
                    return repository.save(product);
                });
    }

    // =========================
    // CHANGE BRANCH
    // =========================
    public Mono<Product> changeBranch(Long id, Long branchId) {

        return repository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Product not found")))
                .flatMap(product -> {
                    product.assignToBranch(branchId);
                    return repository.save(product);
                });
    }

    // =========================
    // DELETE
    // =========================
    public Mono<Void> delete(Long id) {
        return repository.deleteById(id);
    }
}