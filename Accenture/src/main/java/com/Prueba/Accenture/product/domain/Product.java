package com.Prueba.Accenture.product.domain;

import lombok.Getter;

@Getter
public class Product {

    private Long id;
    private String name;
    private Integer stock;
    private Long branchId;

    public Product() {}

    public Product(Long id, String name, Integer stock, Long branchId) {
        this.id = id;
        this.name = name;
        this.stock = stock;
        this.branchId = branchId;
    }

    // =========================
    // DOMAIN LOGIC
    // =========================
    public void rename(String newName) {
        if (newName == null || newName.isBlank()) {
            throw new IllegalArgumentException("Product name cannot be empty");
        }
        this.name = newName;
    }

    public void updateStock(Integer newStock) {
        if (newStock == null || newStock < 0) {
            throw new IllegalArgumentException("Stock cannot be negative");
        }
        this.stock = newStock;
    }

    public void assignToBranch(Long branchId) {
        if (branchId == null) {
            throw new IllegalArgumentException("BranchId cannot be null");
        }
        this.branchId = branchId;
    }
}