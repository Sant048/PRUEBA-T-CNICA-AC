package com.Prueba.Accenture.product.interfaces;

import com.Prueba.Accenture.product.domain.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductRepository {

    Mono<Product> save(Product product);

    Mono<Product> findById(Long id);

    Flux<Product> findByBranchId(Long branchId);

    Mono<Void> deleteById(Long id);

    Mono<Product> updateStock(Long productId, Integer stock);
}
