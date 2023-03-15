package com.seegene.demolambda.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Table
public class Analysis implements Persistable<String> {

    @Id
    String id;

    @NotNull
    String algorithm;

    @NotNull
    @Column(value = "invoke_user")
    String invokeUser;

    @NotNull
    int step;

    @NotNull
    int totalStep;

    @Transient
    private boolean newAnalysis;

    @PersistenceConstructor
    public Analysis(String id, String algorithm, String invokeUser, int step, int totalStep){
        this.id = id;
        this.algorithm = algorithm;
        this.invokeUser = invokeUser;
        this.step = step;
        this.totalStep = totalStep;
    }

    public Analysis(String id, String algorithm, String invokeUser, int step, int totalStep, boolean newAnalysis){
        this(id, algorithm, invokeUser, step, totalStep);
        this.newAnalysis = newAnalysis;
    }

    @Override
    public boolean isNew() {
        return this.newAnalysis || this.id.length() == 0;
    }
}
