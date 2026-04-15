package com.Prueba.Accenture.product.infrastructure;

import com.Prueba.Accenture.product.domain.Product;
import com.Prueba.Accenture.product.infrastructure.ProductEntity;

public class ProductMapper {

    public static Product toDomain(ProductEntity entity) {

        if (entity == null) return null;

        return new Product(
                entity.getId(),
                entity.getName(),
                entity.getStock(),
                entity.getBranchId()
        );
    }

    public static ProductEntity toEntity(Product domain) {

        if (domain == null) return null;

        ProductEntity entity = new ProductEntity();
        entity.setId(domain.getId());
        entity.setName(domain.getName());
        entity.setStock(domain.getStock());
        entity.setBranchId(domain.getBranchId());

        return entity;
    }
}