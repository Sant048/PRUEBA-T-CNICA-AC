package com.Prueba.Accenture.franchise.interfaces.dto;

import lombok.Getter;

@Getter
public class FranchiseRequest {

    private String name;

    public FranchiseRequest() {}

    public FranchiseRequest(String name) {
        this.name = name;
    }

}