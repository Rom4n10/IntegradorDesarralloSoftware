package com.dna.analyzer.service;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DnaPatternAnalyzerTest {

    private final DnaPatternAnalyzer analyzer = new DnaPatternAnalyzer();

    @Test
    void testMutantHorizontal() {
        String[] dna = {"AAAAAA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"};
        assertTrue(analyzer.detectAnomaly(dna));
    }

    @Test
    void testMutantVertical() {
        String[] dna = {"ATGCGA", "AAGTGC", "ATATGT", "AGAAGG", "ACCCCT", "ATCACT"};
        assertTrue(analyzer.detectAnomaly(dna));
    }

    @Test
    void testMutantDiagonalPrincipal() {
        String[] dna = {"ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"};
        assertTrue(analyzer.detectAnomaly(dna));
    }

    @Test
    void testMutantDiagonalInversa() {
        String[] dna = {"ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"};
        assertTrue(analyzer.detectAnomaly(dna));
    }

    @Test
    void testMutantMultipleSequences() {
        String[] dna = {"AAAAAA", "CCCCCC", "GGGGGG", "TTTTTT", "AAAAAA", "CCCCCC"};
        assertTrue(analyzer.detectAnomaly(dna));
    }

    @Test
    void testHumanSimple() {
        String[] dna = {"ATGCGA", "CAGTGC", "TTATTT", "AGACGG", "GCGTCA", "TCACTG"};
        assertFalse(analyzer.detectAnomaly(dna));
    }

    @Test
    void testHumanWithOnlyOneHorizontalSequence() {
        String[] dnaOne = {
                "AAAAGA",
                "CAGTCC",
                "TTATGT",
                "AGACGG",
                "GCGTCA",
                "TCACTG"
        };
        assertFalse(analyzer.detectAnomaly(dnaOne));
    }

    @Test
    void testNullDna() {
        assertFalse(analyzer.detectAnomaly(null));
    }

    @Test
    void testEmptyDna() {
        assertFalse(analyzer.detectAnomaly(new String[]{}));
    }

    @Test
    void testSmallMatrix() {
        String[] dna = {"ATG", "CAG", "TTA"};
        assertFalse(analyzer.detectAnomaly(dna));
    }

    @Test
    void test4x4Mutant() {
        String[] dna = {
                "AAAA",
                "CCCC",
                "TCAG",
                "GGTC"
        };
        assertTrue(analyzer.detectAnomaly(dna));
    }

    @Test
    void test4x4Human() {
        String[] dna = {
                "ATGC",
                "CAGT",
                "TGCA",
                "GCAT"
        };
        assertFalse(analyzer.detectAnomaly(dna));
    }


    @Test
    void test5x5MatrixMutant() {
        String[] dna = {
                "AAAAA",
                "CAGTC",
                "TTATG",
                "AGAAG",
                "CCCCT"
        };
        assertTrue(analyzer.detectAnomaly(dna));
    }

    @Test
    void testRectangularMatrix() {
        String[] dna = {"ATG", "CAGT", "TTA", "AGA"};
        assertFalse(analyzer.detectAnomaly(dna));
    }

    @Test
    void testMutantCrossed() {
        String[] dna = {
                "ATGCGA",
                "CAGTGC",
                "TTATGT",
                "AGAAGG",
                "CCCCTA",
                "TCACTG"
        };
        assertTrue(analyzer.detectAnomaly(dna));
    }

    @Test
    void testAllSameCharacters() {
        String[] dna = {"AAAA", "AAAA", "AAAA", "AAAA"};
        assertTrue(analyzer.detectAnomaly(dna));
    }

    @Test
    void testSequenceAtEndBoundary() {
        String[] dna = {
                "ATGC",
                "CAGT",
                "TGCA",
                "AAAA"
        };
        assertFalse(analyzer.detectAnomaly(dna));
    }

    @Test
    void testLargeMatrixPerformance() {
        int n = 1000; // Matriz grande 1000x1000
        String[] dna = new String[n];
        StringBuilder row = new StringBuilder();
        for(int i=0; i<n; i++) row.append('A');

        for (int i = 0; i < n; i++) {
            dna[i] = row.toString();
        }

        long startTime = System.currentTimeMillis();
        boolean isMutant = analyzer.detectAnomaly(dna);
        long endTime = System.currentTimeMillis();

        assertTrue(isMutant);
        assertTrue((endTime - startTime) < 500, "El algoritmo es demasiado lento para matrices grandes");
    }

    @Test
    void testInvalidCharactersInsideDetector() {
        String[] dna = {"ATGCGA", "CAGTXC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"};
        assertFalse(analyzer.detectAnomaly(dna));
    }

    @Test
    void testNullRowInsideArray() {
        String[] dna = {"ATGCGA", null, "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"};
        assertFalse(analyzer.detectAnomaly(dna));
    }
}