package com.dna.analyzer.service;

import lombok.RequiredArgsConstructor;
import com.dna.analyzer.dto.StatsResponse;
import com.dna.analyzer.repository.AnalyzedDnaRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatsService {

    private final AnalyzedDnaRepository repository;

    public StatsResponse getStats() {
        long mutants = repository.countByIsMutant(true);
        long humans = repository.countByIsMutant(false);
        double ratio = humans == 0 ? 0 : (double) mutants / humans;

        return StatsResponse.builder()
                .countMutantDna(mutants)
                .countHumanDna(humans)
                .ratio(ratio)
                .build();
    }
}