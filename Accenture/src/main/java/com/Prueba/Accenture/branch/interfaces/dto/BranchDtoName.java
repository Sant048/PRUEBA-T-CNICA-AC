package com.Prueba.Accenture.branch.interfaces.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class BranchDtoName {

    @NotBlank
    private String name;
}