package com.Prueba.Accenture.franchise.infrastructure;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("franchise")
public class FranchiseEntity {

    @Id
    private Long id;
    private String name;

    public FranchiseEntity() {}

    public FranchiseEntity(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}