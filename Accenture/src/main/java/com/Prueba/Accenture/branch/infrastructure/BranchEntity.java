package com.Prueba.Accenture.branch.infrastructure;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@AllArgsConstructor
@Table("branch")
public class BranchEntity {

    @Id
    private Long id;
    private String name;
    private Long franchiseId;

    public BranchEntity() {

    }
}
