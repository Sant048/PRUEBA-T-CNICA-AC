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
    // SAVE (INSERT / UPDATE FIXED)
    // =========================
    @Override
    public Mono<Branch> save(Branch branch) {

        if (branch.getId() == null) {

            return client.sql("""
                INSERT INTO branch (name, franchise_id)
                VALUES (:name, :franchiseId)
            """)
                    .bind("name", branch.getName())
                    .bind("franchiseId", branch.getFranchiseId())
                    .fetch()
                    .rowsUpdated()
                    .flatMap(rows -> {
                        if (rows <= 0) {
                            return Mono.error(new RuntimeException("Insert failed"));
                        }
                        return findByUnique(branch.getName(), branch.getFranchiseId());
                    });
        }

        return client.sql("""
            UPDATE branch
            SET name = :name,
                franchise_id = :franchiseId
            WHERE id = :id
        """)
                .bind("id", branch.getId())
                .bind("name", branch.getName())
                .bind("franchiseId", branch.getFranchiseId())
                .fetch()
                .rowsUpdated()
                .flatMap(rows -> {
                    if (rows == 0) {
                        return Mono.error(new RuntimeException("Branch not found: " + branch.getId()));
                    }
                    return findById(branch.getId());
                });
    }

    // =========================
    // FIND BY ID
    // =========================
    @Override
    public Mono<Branch> findById(Long id) {
        return client.sql("""
            SELECT id, name, franchise_id
            FROM branch
            WHERE id = :id
        """)
                .bind("id", id)
                .map(this::mapRow)
                .one();
    }

    // =========================
    // FIND ALL
    // =========================
    @Override
    public Flux<Branch> findAll() {
        return client.sql("""
            SELECT id, name, franchise_id
            FROM branch
        """)
                .map(this::mapRow)
                .all();
    }

    // =========================
    // FIND BY FRANCHISE
    // =========================
    @Override
    public Flux<Branch> findByFranchiseId(Long franchiseId) {
        return client.sql("""
            SELECT id, name, franchise_id
            FROM branch
            WHERE franchise_id = :franchiseId
        """)
                .bind("franchiseId", franchiseId)
                .map(this::mapRow)
                .all();
    }

    // =========================
    // DELETE
    // =========================
    @Override
    public Mono<Void> deleteById(Long id) {
        return client.sql("""
            DELETE FROM branch
            WHERE id = :id
        """)
                .bind("id", id)
                .fetch()
                .rowsUpdated()
                .flatMap(rows -> {
                    if (rows == 0) {
                        return Mono.error(new RuntimeException("Branch not found: " + id));
                    }
                    return Mono.empty();
                });
    }

    // =========================
    // FIX INSERT RETURN (SAFE)
    // =========================
    private Mono<Branch> findByUnique(String name, Long franchiseId) {
        return client.sql("""
            SELECT id, name, franchise_id
            FROM branch
            WHERE name = :name AND franchise_id = :franchiseId
            ORDER BY id DESC
            LIMIT 1
        """)
                .bind("name", name)
                .bind("franchiseId", franchiseId)
                .map(this::mapRow)
                .one();
    }

    // =========================
    // MAPPER CENTRALIZADO
    // =========================
    private Branch mapRow(io.r2dbc.spi.Row row, io.r2dbc.spi.RowMetadata meta) {
        return BranchMapper.toDomain(
                new BranchEntity(
                        row.get("id", Long.class),
                        row.get("name", String.class),
                        row.get("franchise_id", Long.class)
                )
        );
    }

    public Mono<Branch> changeFranchise(Long id, Long franchiseId) {

        return client.sql("""
        UPDATE branch
        SET franchise_id = :franchiseId
        WHERE id = :id
    """)
                .bind("id", id)
                .bind("franchiseId", franchiseId)
                .fetch()
                .rowsUpdated()
                .flatMap(rows -> {
                    if (rows == 0) {
                        return Mono.error(new RuntimeException("Branch not found: " + id));
                    }
                    return findById(id);
                });
    }
}