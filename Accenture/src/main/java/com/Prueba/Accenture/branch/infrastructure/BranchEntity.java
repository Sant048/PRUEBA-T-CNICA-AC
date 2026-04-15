package com.Prueba.Accenture.branch.infrastructure;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@Table("branch")
public class BranchEntity {

    @Id
    private Long id;
    private String name;
    private Long franchiseId;
}
