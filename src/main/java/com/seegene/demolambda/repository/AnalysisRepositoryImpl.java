package com.seegene.demolambda.repository;

import com.seegene.demolambda.dto.AnalysisResDto;
import com.seegene.demolambda.entity.Analysis;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.r2dbc.core.DatabaseClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@RequiredArgsConstructor
@Slf4j
public class AnalysisRepositoryImpl implements AnalysisRepositoryCustom{
    private final DatabaseClient databaseClient;

    public Mono<AnalysisResDto> updateAnalysis(String id) {
        int randomValue = 1;//((int) (Math.random()*20)+1);

        String sql = """
                   UPDATE analysis
                   SET step = step+1
                 WHERE id   = :id
                   AND step < total_step
                   AND 0    = duration_1(:duration)""";
        //log.info(sql);

        return databaseClient
                .sql(sql)
                .bind("id", id)
                .bind("duration", randomValue)
                .fetch()
                .one()
                .map(AnalysisResDto::from);
    }
}
