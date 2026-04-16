package com.Prueba.Accenture.branch.interfaces.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class BranchRequest {

    @NotBlank
    private String name;

    @NotNull
    private Long franchiseId;

    // getters y setters
}