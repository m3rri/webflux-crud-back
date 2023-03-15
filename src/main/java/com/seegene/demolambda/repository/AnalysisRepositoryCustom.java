package com.seegene.demolambda.repository;

import com.seegene.demolambda.dto.AnalysisResDto;
import com.seegene.demolambda.entity.Analysis;
import org.springframework.data.r2dbc.repository.Modifying;
import reactor.core.publisher.Mono;

import java.util.Map;

public interface AnalysisRepositoryCustom {
    @Modifying
    Mono<AnalysisResDto> updateAnalysis(String id);
}
