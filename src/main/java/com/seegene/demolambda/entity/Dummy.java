package com.seegene.demolambda.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@Table
public class Dummy {
    @Id
    int id;

    @NotNull
    String sequence;
}
