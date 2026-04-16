package com.Prueba.Accenture.product.interfaces.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductDtoStock  {

    @NotNull
    private Integer stock;

}