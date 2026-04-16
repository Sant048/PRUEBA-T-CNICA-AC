package com.Prueba.Accenture.report.infrastructure;

import com.Prueba.Accenture.report.domain.ReportRepository;
import com.Prueba.Accenture.report.domain.TopProduct;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public class ReportRepositoryImpl implements ReportRepository {

    private final DatabaseClient client;

    public ReportRepositoryImpl(DatabaseClient client) {
        this.client = client;
    }

    @Override
    public Flux<TopProduct> findTopProductsByFranchise(Long franchiseId) {

        return client.sql("""
                SELECT p.branch_id,
                       p.id AS product_id,
                       p.name AS product_name,
                       p.stock
                FROM product p
                WHERE p.branch_id IN (
                    SELECT id FROM branch WHERE franchise_id = :franchiseId
                )
                AND p.stock = (
                    SELECT MAX(p2.stock)
                    FROM product p2
                    WHERE p2.branch_id = p.branch_id
                )
                """)
                .bind("franchiseId", franchiseId)
                .map((row, meta) -> new TopProduct(
                        row.get("branch_id", Long.class),
                        row.get("product_id", Long.class),
                        row.get("product_name", String.class),
                        row.get("stock", Integer.class)
                ))
                .all();
    }
}