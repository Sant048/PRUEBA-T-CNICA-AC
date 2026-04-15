package com.Prueba.Accenture.product.infrastructure;

import com.Prueba.Accenture.product.domain.Product;
import com.Prueba.Accenture.product.domain.ProductRepository;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class ProductRepositoryImpl implements ProductRepository {

    private final DatabaseClient client;

    public ProductRepositoryImpl(DatabaseClient client) {
        this.client = client;
    }

    // =========================
    // SAVE
    // =========================
    @Override
    public Mono<Product> save(Product product) {

        if (product.getId() == null) {
            // INSERT
            return client.sql("""
                    INSERT INTO product (name, stock, branch_id)
                    VALUES (:name, :stock, :branchId)
                    """)
                    .bind("name", product.getName())
                    .bind("stock", product.getStock())
                    .bind("branchId", product.getBranchId())
                    .fetch()
                    .rowsUpdated()
                    .flatMap(rows -> findLastInserted());
        } else {
            // UPDATE
            return client.sql("""
                    UPDATE product 
                    SET name = :name, stock = :stock, branch_id = :branchId
                    WHERE id = :id
                    """)
                    .bind("id", product.getId())
                    .bind("name", product.getName())
                    .bind("stock", product.getStock())
                    .bind("branchId", product.getBranchId())
                    .fetch()
                    .rowsUpdated()
                    .thenReturn(product);
        }
    }

    // =========================
    // FIND BY ID
    // =========================
    @Override
    public Mono<Product> findById(Long id) {

        return client.sql("""
                SELECT id, name, stock, branch_id 
                FROM product 
                WHERE id = :id
                """)
                .bind("id", id)
                .map((row, meta) -> new Product(
                        row.get("id", Long.class),
                        row.get("name", String.class),
                        row.get("stock", Integer.class),
                        row.get("branch_id", Long.class)
                ))
                .one();
    }

    // =========================
    // FIND ALL
    // =========================
    @Override
    public Flux<Product> findAll() {

        return client.sql("""
                SELECT id, name, stock, branch_id 
                FROM product
                """)
                .map((row, meta) -> new Product(
                        row.get("id", Long.class),
                        row.get("name", String.class),
                        row.get("stock", Integer.class),
                        row.get("branch_id", Long.class)
                ))
                .all();
    }

    // =========================
    // FIND BY BRANCH
    // =========================
    @Override
    public Flux<Product> findByBranchId(Long branchId) {

        return client.sql("""
                SELECT id, name, stock, branch_id 
                FROM product 
                WHERE branch_id = :branchId
                """)
                .bind("branchId", branchId)
                .map((row, meta) -> new Product(
                        row.get("id", Long.class),
                        row.get("name", String.class),
                        row.get("stock", Integer.class),
                        row.get("branch_id", Long.class)
                ))
                .all();
    }

    // =========================
    // DELETE
    // =========================
    @Override
    public Mono<Void> deleteById(Long id) {

        return client.sql("""
                DELETE FROM product WHERE id = :id
                """)
                .bind("id", id)
                .fetch()
                .rowsUpdated()
                .then();
    }

    // =========================
    // SAFE FETCH LAST INSERT
    // =========================
    private Mono<Product> findLastInserted() {

        return client.sql("""
                SELECT id, name, stock, branch_id 
                FROM product 
                ORDER BY id DESC 
                LIMIT 1
                """)
                .map((row, meta) -> new Product(
                        row.get("id", Long.class),
                        row.get("name", String.class),
                        row.get("stock", Integer.class),
                        row.get("branch_id", Long.class)
                ))
                .one();
    }
}