package com.Prueba.Accenture.franchise.domain;

public class Franchise {

    private Long id;
    private String name;

    public Franchise() {
    }

    public Franchise(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    // GETTERS (sin Lombok para evitar conflictos en proyectos grandes)
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    // Lógica de dominio
    public void rename(String newName) {
        if (newName == null || newName.isBlank()) {
            throw new IllegalArgumentException("Franchise name cannot be empty");
        }
        this.name = newName;
    }
}