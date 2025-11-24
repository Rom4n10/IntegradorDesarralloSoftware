package org.example.service;

import org.example.entity.DnaRecord;
import org.example.repository.DnaRecordRepository;
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
class MutantServiceTest {

    @Mock
    private DnaRecordRepository repository;

    @Mock
    private MutantDetector detector;

    @InjectMocks
    private MutantService service;

    @Test
    void testAnalyzeNewDnaMutant() {
        String[] dna = {"ATGCGA"};
        when(repository.findByDnaHash(anyString())).thenReturn(Optional.empty());
        when(detector.isMutant(dna)).thenReturn(true);

        boolean result = service.analyze(dna);

        assertTrue(result);
        verify(repository, times(1)).save(any(DnaRecord.class));
    }

    @Test
    void testAnalyzeCachedDnaMutant() {
        String[] dna = {"ATGCGA"};
        DnaRecord existing = DnaRecord.builder().dnaHash("hash").isMutant(true).build();

        when(repository.findByDnaHash(anyString())).thenReturn(Optional.of(existing));

        boolean result = service.analyze(dna);

        assertTrue(result);
        verify(repository, never()).save(any(DnaRecord.class));
        verify(detector, never()).isMutant(any());
    }

    @Test
    void testAnalyzeNewDnaHuman() {
        String[] dna = {"AAAA"};
        when(repository.findByDnaHash(anyString())).thenReturn(Optional.empty());
        when(detector.isMutant(dna)).thenReturn(false);

        boolean result = service.analyze(dna);

        assertFalse(result);
        verify(repository, times(1)).save(any(DnaRecord.class));
    }

    @Test
    void testAnalyzeCachedDnaHuman() {
        String[] dna = {"AAAA"};
        DnaRecord existing = DnaRecord.builder().dnaHash("hash").isMutant(false).build();

        when(repository.findByDnaHash(anyString())).thenReturn(Optional.of(existing));

        boolean result = service.analyze(dna);

        assertFalse(result);
        verify(repository, never()).save(any(DnaRecord.class));
        verify(detector, never()).isMutant(any());
    }

    @Test
    void testDnaHashCalculationConsistency() {
        String[] dna = {"ATGCGA"};
        when(repository.findByDnaHash(anyString())).thenReturn(Optional.empty());
        when(detector.isMutant(dna)).thenReturn(true);

        service.analyze(dna);

        verify(repository, times(1)).findByDnaHash(anyString());
    }
}