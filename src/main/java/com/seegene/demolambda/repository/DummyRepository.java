package com.seegene.demolambda.repository;

import com.seegene.demolambda.entity.Dummy;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface DummyRepository extends R2dbcRepository<Dummy, Integer> {
}
