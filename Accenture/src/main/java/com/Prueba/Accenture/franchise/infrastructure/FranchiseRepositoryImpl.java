package com.Prueba.Accenture.franchise.infrastructure;

import com.Prueba.Accenture.franchise.domain.Franchise;
import com.Prueba.Accenture.franchise.domain.FranchiseRepository;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class FranchiseRepositoryImpl implements FranchiseRepository {

    private final DatabaseClient client;

    public FranchiseRepositoryImpl(DatabaseClient client) {
        this.client = client;
    }

    // =========================
    // SAVE (INSERT / UPDATE)
    // =========================
    @Override
    public Mono<Franchise> save(Franchise f) {

        if (f.getId() == null) {
            return client.sql("""
                    INSERT INTO franchise (name)
                    VALUES (:name)
                """)
                    .bind("name", f.getName())
                    .fetch()
                    .rowsUpdated()
                    .then(
                            client.sql("SELECT id, name FROM franchise WHERE id = LAST_INSERT_ID()")
                                    .map((row, meta) -> new Franchise(
                                            row.get("id", Long.class),
                                            row.get("name", String.class)
                                    ))
                                    .one()
                    );
        }

        return client.sql("""
                UPDATE franchise 
                SET name = :name 
                WHERE id = :id
            """)
                .bind("name", f.getName())
                .bind("id", f.getId())
                .fetch()
                .rowsUpdated()
                .then(
                        client.sql("SELECT id, name FROM franchise WHERE id = :id")
                                .bind("id", f.getId())
                                .map((row, meta) -> new Franchise(
                                        row.get("id", Long.class),
                                        row.get("name", String.class)
                                ))
                                .one()
                );
    }

    // =========================
    // FIND BY ID
    // =========================
    @Override
    public Mono<Franchise> findById(Long id) {

        return client.sql("SELECT id, name FROM franchise WHERE id = :id")
                .bind("id", id)
                .map((row, meta) -> new Franchise(
                        row.get("id", Long.class),
                        row.get("name", String.class)
                ))
                .one();
    }

    // =========================
    // FIND ALL
    // =========================
    @Override
    public Flux<Franchise> findAll() {

        return client.sql("SELECT id, name FROM franchise")
                .map((row, meta) -> new Franchise(
                        row.get("id", Long.class),
                        row.get("name", String.class)
                ))
                .all();
    }

    // =========================
    // DELETE
    // =========================
    @Override
    public Mono<Void> deleteById(Long id) {

        return client.sql("DELETE FROM franchise WHERE id = :id")
                .bind("id", id)
                .fetch()
                .rowsUpdated()
                .then();
    }
}