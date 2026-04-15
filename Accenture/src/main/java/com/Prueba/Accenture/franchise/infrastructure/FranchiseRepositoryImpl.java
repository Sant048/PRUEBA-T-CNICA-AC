package com.Prueba.Accenture.franchise.infrastructure;

import com.Prueba.Accenture.franchise.domain.Franchise;
import com.Prueba.Accenture.franchise.domain.FranchiseRepository;
import com.Prueba.Accenture.franchise.infrastructure.Mapper.FranchiseMapper;
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
    // SAVE
    // =========================
    @Override
    public Mono<Franchise> save(Franchise franchise) {

        return client.sql("INSERT INTO franchise (name) VALUES (:name)")
                .bind("name", franchise.getName())
                .fetch()
                .rowsUpdated()
                .flatMap(rows -> franchiseAfterInsert());
    }

    // =========================
    // FIND BY ID
    // =========================
    @Override
    public Mono<Franchise> findById(Long id) {

        return client.sql("SELECT id, name FROM franchise WHERE id = :id")
                .bind("id", id)
                .map((row, meta) ->
                        FranchiseMapper.toDomain(
                                new FranchiseEntity(
                                        row.get("id", Long.class),
                                        row.get("name", String.class)
                                )
                        )
                )
                .one();
    }

    // =========================
    // FIND ALL
    // =========================
    @Override
    public Flux<Franchise> findAll() {

        return client.sql("SELECT id, name FROM franchise")
                .map((row, meta) ->
                        FranchiseMapper.toDomain(
                                new FranchiseEntity(
                                        row.get("id", Long.class),
                                        row.get("name", String.class)
                                )
                        )
                )
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

    // =========================
    // SAFE INSERT FETCH
    // =========================
    private Mono<Franchise> franchiseAfterInsert() {

        return client.sql("SELECT id, name FROM franchise ORDER BY id DESC LIMIT 1")
                .map((row, meta) ->
                        FranchiseMapper.toDomain(
                                new FranchiseEntity(
                                        row.get("id", Long.class),
                                        row.get("name", String.class)
                                )
                        )
                )
                .one();
    }
}