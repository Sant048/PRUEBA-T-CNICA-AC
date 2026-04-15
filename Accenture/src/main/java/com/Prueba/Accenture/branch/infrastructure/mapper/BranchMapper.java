package com.Prueba.Accenture.branch.infrastructure.mapper;

import com.Prueba.Accenture.branch.domain.Branch;
import com.Prueba.Accenture.branch.infrastructure.BranchEntity;

public class BranchMapper {

    public static Branch toDomain(BranchEntity entity) {

        if (entity == null) return null;

        return new Branch(
                entity.getId(),
                entity.getName(),
                entity.getFranchiseId()
        );
    }

    public static BranchEntity toEntity(Branch domain) {

        if (domain == null) return null;

        BranchEntity entity = new BranchEntity();
        entity.setId(domain.getId());
        entity.setName(domain.getName());
        entity.setFranchiseId(domain.getFranchiseId());

        return entity;
    }
}