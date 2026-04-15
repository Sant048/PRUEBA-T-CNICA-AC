package com.Prueba.Accenture.branch.domain;

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

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getFranchiseId() {
        return franchiseId;
    }
}