package com.dna.analyzer.service;

import com.dna.analyzer.entity.AnalyzedDna;
import com.dna.analyzer.repository.AnalyzedDnaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DnaAnalysisServiceTest {

    @Mock
    private AnalyzedDnaRepository repository;

    @Mock
    private DnaPatternAnalyzer analyzer;

    @InjectMocks
    private DnaAnalysisService service;

    @Test
    void testProcessNewDnaMutant() {
        String[] dna = {"ATGCGA"};
        when(repository.findByDnaHash(anyString())).thenReturn(Optional.empty());
        when(analyzer.detectAnomaly(dna)).thenReturn(true);

        boolean result = service.processDna(dna);

        assertTrue(result);
        verify(repository, times(1)).save(any(AnalyzedDna.class));
    }

    @Test
    void testProcessCachedDnaMutant() {
        String[] dna = {"ATGCGA"};
        AnalyzedDna existing = AnalyzedDna.builder().dnaHash("hash").isMutant(true).build();

        when(repository.findByDnaHash(anyString())).thenReturn(Optional.of(existing));

        boolean result = service.processDna(dna);

        assertTrue(result);
        verify(repository, never()).save(any(AnalyzedDna.class));
        verify(analyzer, never()).detectAnomaly(any());
    }

    @Test
    void testProcessNewDnaHuman() {
        String[] dna = {"AAAA"};
        when(repository.findByDnaHash(anyString())).thenReturn(Optional.empty());
        when(analyzer.detectAnomaly(dna)).thenReturn(false);

        boolean result = service.processDna(dna);

        assertFalse(result);
        verify(repository, times(1)).save(any(AnalyzedDna.class));
    }

    @Test
    void testProcessCachedDnaHuman() {
        String[] dna = {"AAAA"};
        AnalyzedDna existing = AnalyzedDna.builder().dnaHash("hash").isMutant(false).build();

        when(repository.findByDnaHash(anyString())).thenReturn(Optional.of(existing));

        boolean result = service.processDna(dna);

        assertFalse(result);
        verify(repository, never()).save(any(AnalyzedDna.class));
        verify(analyzer, never()).detectAnomaly(any());
    }

    @Test
    void testDnaHashCalculationConsistency() {
        String[] dna = {"ATGCGA"};
        when(repository.findByDnaHash(anyString())).thenReturn(Optional.empty());
        when(analyzer.detectAnomaly(dna)).thenReturn(true);

        service.processDna(dna);

        verify(repository, times(1)).findByDnaHash(anyString());
    }
}