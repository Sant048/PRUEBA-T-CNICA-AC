package com.Prueba.Accenture.branch.domain;

import lombok.Getter;

@Getter
public class Branch {

    private Long id;
    private String name;
    private Long franchiseId;

    public Branch() {}

    public Branch(Long id, String name, Long franchiseId) {
        this.id = id;
        this.name = name;
        this.franchiseId = franchiseId;
    }

    public void rename(String newName) {
        if (newName == null || newName.isBlank()) {
            throw new IllegalArgumentException("Branch name cannot be empty");
        }
        this.name = newName;
    }

    public void assignToFranchise(Long franchiseId) {
        if (franchiseId == null) {
            throw new IllegalArgumentException("FranchiseId cannot be null");
        }
        this.franchiseId = franchiseId;
    }
}