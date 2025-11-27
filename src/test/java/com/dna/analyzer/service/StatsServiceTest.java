package com.dna.analyzer.service;

import com.dna.analyzer.dto.StatsResponse;
import com.dna.analyzer.repository.AnalyzedDnaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StatsServiceTest {

    @Mock
    private AnalyzedDnaRepository repository;

    @InjectMocks
    private StatsService service;

    @Test
    void testGetStats_NormalCase() {
        when(repository.countByIsMutant(true)).thenReturn(40L);
        when(repository.countByIsMutant(false)).thenReturn(100L);

        StatsResponse response = service.getStats();

        assertEquals(40, response.getCountMutantDna());
        assertEquals(100, response.getCountHumanDna());
        assertEquals(0.4, response.getRatio());
    }

    @Test
    void testGetStats_ZeroHumans() {
        when(repository.countByIsMutant(true)).thenReturn(10L);
        when(repository.countByIsMutant(false)).thenReturn(0L);

        StatsResponse response = service.getStats();

        assertEquals(10, response.getCountMutantDna());
        assertEquals(0, response.getCountHumanDna());
        assertEquals(0.0, response.getRatio());
    }

    @Test
    void testGetStats_ZeroMutants() {
        when(repository.countByIsMutant(true)).thenReturn(0L);
        when(repository.countByIsMutant(false)).thenReturn(50L);

        StatsResponse response = service.getStats();

        assertEquals(0, response.getCountMutantDna());
        assertEquals(50, response.getCountHumanDna());
        assertEquals(0.0, response.getRatio());
    }

    @Test
    void testGetStats_EmptyDatabase() {
        when(repository.countByIsMutant(true)).thenReturn(0L);
        when(repository.countByIsMutant(false)).thenReturn(0L);

        StatsResponse response = service.getStats();

        assertEquals(0, response.getCountMutantDna());
        assertEquals(0, response.getCountHumanDna());
        assertEquals(0.0, response.getRatio());
    }

    @Test
    void testGetStats_EqualCounts() {
        when(repository.countByIsMutant(true)).thenReturn(50L);
        when(repository.countByIsMutant(false)).thenReturn(50L);

        StatsResponse response = service.getStats();

        assertEquals(50, response.getCountMutantDna());
        assertEquals(50, response.getCountHumanDna());
        assertEquals(1.0, response.getRatio());
    }

    @Test
    void testGetStats_RecurringDecimal() {
        when(repository.countByIsMutant(true)).thenReturn(1L);
        when(repository.countByIsMutant(false)).thenReturn(3L);

        StatsResponse response = service.getStats();

        assertEquals(0.3333, response.getRatio(), 0.0001);
    }
}