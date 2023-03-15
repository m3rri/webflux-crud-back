package com.seegene.demolambda.dto;

import com.seegene.demolambda.entity.Analysis;
import com.seegene.demolambda.enums.Algorithm;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@Builder
public class AnalysisResDto {
    private String id;
    private Algorithm algorithm;
    private String invokeUser;
    private int step;
    private int totalStep;
    private boolean complete;

    public static AnalysisResDto from(Analysis analysis){
        Algorithm algorithm = Algorithm.fromCode(analysis.getAlgorithm());

        return AnalysisResDto.builder()
                .id(analysis.getId())
                .algorithm(algorithm)
                .step(analysis.getStep())
                .totalStep(algorithm.value)
                .invokeUser(analysis.getInvokeUser())
                .build();
    }

    public static AnalysisResDto from(Map<String, Object> analysis){
        Algorithm algorithm = Algorithm.fromCode((String) analysis.get("algorithm"));

        return AnalysisResDto.builder()
                .id((String) analysis.get("id"))
                .algorithm(algorithm)
                .step((Integer) analysis.get("step"))
                .totalStep(algorithm.value)
                .invokeUser((String) analysis.get("invoke_user"))
                .build();
    }

}
