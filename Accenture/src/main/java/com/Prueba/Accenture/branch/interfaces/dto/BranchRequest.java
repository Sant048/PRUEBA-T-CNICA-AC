package com.Prueba.Accenture.branch.interfaces.dto;

import lombok.Getter;
import lombok.AllArgsConstructor;

@Getter
@AllArgsConstructor
public class BranchRequest {

    private String name;
    private Long franchiseId;
}
