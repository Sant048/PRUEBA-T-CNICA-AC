package com.Prueba.Accenture.product.domain;

public class Product {

    private Long id;
    private String name;
    private int stock;
    private Long branchId;

    public Product() {}

    public Product(Long id, String name, int stock, Long branchId) {
        this.id = id;
        this.name = name;
        this.stock = stock;
        this.branchId = branchId;
    }

    public void increaseStock(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be > 0");
        }
        this.stock += amount;
    }

    public void decreaseStock(int amount) {
        if (amount <= 0 || amount > this.stock) {
            throw new IllegalArgumentException("Invalid stock decrease");
        }
        this.stock -= amount;
    }

    public void rename(String newName) {
        if (newName == null || newName.isBlank()) {
            throw new IllegalArgumentException("Product name cannot be empty");
        }
        this.name = newName;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getStock() {
        return stock;
    }

    public Long getBranchId() {
        return branchId;
    }
}
