package com.seegene.demolambda.service;

import com.seegene.demolambda.dto.AnalysisResDto;
import com.seegene.demolambda.entity.Analysis;
import com.seegene.demolambda.enums.Algorithm;
import com.seegene.demolambda.repository.AnalysisRepository;
import com.seegene.demolambda.repository.AnalysisRepositoryImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class AnalysisService {
    private final AnalysisRepository analysisRepository;
    private final AnalysisRepositoryImpl analysisRepositoryImpl;

    public Flux<AnalysisResDto> getAnalysisList(String invokeUser){
        return analysisRepository
                .findAllByInvokeUser(invokeUser)
                .map(AnalysisResDto::from);
    }

    public Mono<AnalysisResDto> getAnalysis(String id){
        //log.info("getAnalysis", id);
        return analysisRepository
                .findById(id)
                .map(AnalysisResDto::from);
    }

    public Mono<AnalysisResDto> addAnalysis(String id, String algorithm, String invokeUser){
        Analysis analysis = new Analysis(id, algorithm, invokeUser, 0, Algorithm.fromCode(algorithm).value, true);

        return analysisRepository
                .save(analysis)
                .map(AnalysisResDto::from);
    }

    public Flux<Integer> updateAnalysis(String id, int repeatValue){
        //log.info("updateAnalysis", id);

//        return analysisRepositoryImpl
//                .updateAnalysis(id)
//                .repeat(repeatValue-1);
        return analysisRepository
                .updateAnalysis(id, (int) (Math.random()*5)+1)
                .repeat(repeatValue-1);
    }
}
