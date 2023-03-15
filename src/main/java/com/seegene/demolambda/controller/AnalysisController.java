package com.seegene.demolambda.controller;

import com.seegene.demolambda.dto.AnalysisResDto;
import com.seegene.demolambda.service.AnalysisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.Map;

@RestController
@RequestMapping("/analysis")
@Slf4j
@RequiredArgsConstructor
public class AnalysisController {
    private final AnalysisService analysisService;
    private final Map<String, Sinks.Many<AnalysisResDto>> sinkMap;

    @GetMapping("/{invokeUser}")
    public Flux<ServerSentEvent<AnalysisResDto>> getList(@PathVariable String invokeUser){
        Flux<AnalysisResDto> initialList = analysisService.getAnalysisList(invokeUser);
        if(sinkMap.get(invokeUser) == null){
            sinkMap.put(invokeUser, Sinks.many().multicast().onBackpressureBuffer());
        }

        return Flux.merge(initialList, sinkMap.get(invokeUser).asFlux())
                .map(analysisResDto->ServerSentEvent.builder(analysisResDto).build())
                ;
    }

    @PostMapping("/{invokeUser}/{id}")
    public Flux<AnalysisResDto> addAnalysis(@PathVariable String invokeUser,
                                       @PathVariable String id,
                                       @RequestParam String algorithm){
        if(sinkMap.get(invokeUser) == null){
            sinkMap.put(invokeUser, Sinks.many().multicast().onBackpressureBuffer());
        }

        return analysisService
                .addAnalysis(id, algorithm, invokeUser)
                .doOnNext((analysisResDto)->{
                    //log.info("왜 안와");
                    sinkMap.get(invokeUser).tryEmitNext(analysisResDto);

                    int repeatValue = analysisResDto.getAlgorithm().value;

                    analysisService
                        .updateAnalysis(id, repeatValue)
                        .doOnNext(i->
                            analysisService
                                .getAnalysis(id)
                                .doOnNext(sinkMap.get(invokeUser)::tryEmitNext)
                                .subscribe()
                        )
                        .subscribe();
                    //https://stackoverflow.com/questions/61717654/update-using-query-not-updating-data-in-reactivecrudrepository
                })
                .flux();
    }
}
