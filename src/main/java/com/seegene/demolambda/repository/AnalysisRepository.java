package com.seegene.demolambda.repository;

import com.seegene.demolambda.entity.Analysis;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AnalysisRepository extends R2dbcRepository<Analysis, String> {
    @Query("SELECT * FROM analysis WHERE invoke_user = :invokeUser")
    Flux<Analysis> findAllByInvokeUser(String invokeUser);

    @Modifying
    @Query("""
        UPDATE analysis
           SET step = step+1
         WHERE id   = :id
           AND step < total_step
           AND 0    = duration_1(:duration)
    """)
    Mono<Integer> updateAnalysis(String id, int duration);
}
