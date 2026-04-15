package com.Prueba.Accenture.franchise.infrastructure.Mapper;

import com.Prueba.Accenture.franchise.domain.Franchise;
import com.Prueba.Accenture.franchise.infrastructure.FranchiseEntity;

public class FranchiseMapper {

    private FranchiseMapper() {
        // evita instanciación
    }

    public static Franchise toDomain(FranchiseEntity entity) {

        if (entity == null) return null;

        return new Franchise(
                entity.getId(),
                entity.getName()
        );
    }

    public static FranchiseEntity toEntity(Franchise domain) {

        if (domain == null) return null;

        return new FranchiseEntity(
                domain.getId(),
                domain.getName()
        );
    }
}