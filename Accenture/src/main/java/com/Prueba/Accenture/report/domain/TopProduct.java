package com.Prueba.Accenture.report.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TopProduct {

    private Long branchId;
    private Long productId;
    private String productName;
    private Integer stock;
}