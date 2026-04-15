package com.Prueba.Accenture.product.infrastructure;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@Table("product")
public class ProductEntity {

    @Id
    private Long id;

    private String name;
    private Integer stock;
    private Long branchId;
}