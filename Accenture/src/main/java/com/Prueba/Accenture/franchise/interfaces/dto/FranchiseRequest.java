package com.Prueba.Accenture.franchise.interfaces.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class FranchiseRequest {
    @NotBlank
    private String name;

    public FranchiseRequest() {}

    public FranchiseRequest(String name) {
        this.name = name;
    }

}