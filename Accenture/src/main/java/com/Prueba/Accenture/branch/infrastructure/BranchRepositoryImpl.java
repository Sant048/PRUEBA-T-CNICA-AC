package com.Prueba.Accenture.branch.infrastructure;

import com.Prueba.Accenture.branch.domain.Branch;
import com.Prueba.Accenture.branch.domain.BranchRepository;
import com.Prueba.Accenture.branch.infrastructure.mapper.BranchMapper;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class BranchRepositoryImpl implements BranchRepository {

    private final DatabaseClient client;

    public BranchRepositoryImpl(DatabaseClient client) {
        this.client = client;
    }

    // =========================
    // SAVE
    // =========================
    @Override
    public Mono<Branch> save(Branch branch) {

        return client.sql("INSERT INTO branch (name, franchise_id) VALUES (:name, :franchiseId)")
                .bind("name", branch.getName())
                .bind("franchiseId", branch.getFranchiseId())
                .fetch()
                .rowsUpdated()
                .flatMap(rows -> findLastInserted());
    }

    // =========================
    // FIND BY ID
    // =========================
    @Override
    public Mono<Branch> findById(Long id) {

        return client.sql("SELECT id, name, franchise_id FROM branch WHERE id = :id")
                .bind("id", id)
                .map((row, meta) -> {
                    BranchEntity entity = new BranchEntity();
                    entity.setId(row.get("id", Long.class));
                    entity.setName(row.get("name", String.class));
                    entity.setFranchiseId(row.get("franchise_id", Long.class));
                    return BranchMapper.toDomain(entity);
                })
                .one();
    }

    // =========================
    // FIND ALL
    // =========================
    @Override
    public Flux<Branch> findAll() {

        return client.sql("SELECT id, name, franchise_id FROM branch")
                .map((row, meta) -> {
                    BranchEntity entity = new BranchEntity();
                    entity.setId(row.get("id", Long.class));
                    entity.setName(row.get("name", String.class));
                    entity.setFranchiseId(row.get("franchise_id", Long.class));
                    return BranchMapper.toDomain(entity);
                })
                .all();
    }

    // =========================
    // FIND BY FRANCHISE
    // =========================
    @Override
    public Flux<Branch> findByFranchiseId(Long franchiseId) {

        return client.sql("SELECT id, name, franchise_id FROM branch WHERE franchise_id = :franchiseId")
                .bind("franchiseId", franchiseId)
                .map((row, meta) -> {
                    BranchEntity entity = new BranchEntity();
                    entity.setId(row.get("id", Long.class));
                    entity.setName(row.get("name", String.class));
                    entity.setFranchiseId(row.get("franchise_id", Long.class));
                    return BranchMapper.toDomain(entity);
                })
                .all();
    }

    // =========================
    // DELETE
    // =========================
    @Override
    public Mono<Void> deleteById(Long id) {

        return client.sql("DELETE FROM branch WHERE id = :id")
                .bind("id", id)
                .fetch()
                .rowsUpdated()
                .then();
    }

    // =========================
    // SAFE INSERT FETCH
    // =========================
    private Mono<Branch> findLastInserted() {

        return client.sql("SELECT id, name, franchise_id FROM branch ORDER BY id DESC LIMIT 1")
                .map((row, meta) -> {
                    BranchEntity entity = new BranchEntity();
                    entity.setId(row.get("id", Long.class));
                    entity.setName(row.get("name", String.class));
                    entity.setFranchiseId(row.get("franchise_id", Long.class));
                    return BranchMapper.toDomain(entity);
                })
                .one();
    }
}