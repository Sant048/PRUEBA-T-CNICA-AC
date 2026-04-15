package com.Prueba.Accenture.product.interfaces;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductRequest {

    private String name;
    private Integer stock;
    private Long branchId;
}