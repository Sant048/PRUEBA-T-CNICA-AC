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
    public Flux<TopProduct> findTopProductsByBranch() {

        return client.sql("""
                SELECT p.branch_id, p.id, p.name, p.stock
                FROM product p
                INNER JOIN (
                    SELECT branch_id, MAX(stock) AS max_stock
                    FROM product
                    GROUP BY branch_id
                ) grouped
                ON p.branch_id = grouped.branch_id
                AND p.stock = grouped.max_stock
                """)
                .map((row, meta) -> new TopProduct(
                        row.get("branch_id", Long.class),
                        row.get("id", Long.class),
                        row.get("name", String.class),
                        row.get("stock", Integer.class)
                ))
                .all();
    }
}