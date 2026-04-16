package com.Prueba.Accenture.product.interfaces;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductRequest {

    @NotBlank
    private String name;

    @NotNull
    private Integer stock;

    @NotNull
    private Long branchId;
}