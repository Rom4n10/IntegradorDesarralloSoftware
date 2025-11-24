package org.example.service;

import org.springframework.stereotype.Component;
import java.util.Set;

@Component
public class MutantDetector {

    private static final int SEQUENCE_LENGTH = 4;

    private static final Set<Character> VALID_BASES = Set.of('A', 'T', 'C', 'G');

    public boolean isMutant(String[] dna) {
        if (dna == null) return false;

        int n = dna.length;
        if (n == 0) return false;

        if (n < SEQUENCE_LENGTH) return false;

        char[][] matrix = new char[n][];

        for (int i = 0; i < n; i++) {
            if (dna[i] == null || dna[i].length() != n) {
                return false;
            }


            for (char c : dna[i].toCharArray()) {
                if (!VALID_BASES.contains(c)) {
                    return false;
                }
            }

            matrix[i] = dna[i].toCharArray();
        }

        int sequencesFound = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {


                if (sequencesFound > 1) return true;

                char base = matrix[i][j];


                if (j <= n - SEQUENCE_LENGTH) {
                    if (base == matrix[i][j+1] &&
                            base == matrix[i][j+2] &&
                            base == matrix[i][j+3]) {
                        sequencesFound++;
                        if (sequencesFound > 1) return true;
                    }
                }

                if (i <= n - SEQUENCE_LENGTH) {
                    if (base == matrix[i+1][j] &&
                            base == matrix[i+2][j] &&
                            base == matrix[i+3][j]) {
                        sequencesFound++;
                        if (sequencesFound > 1) return true;
                    }
                }

                if (i <= n - SEQUENCE_LENGTH && j <= n - SEQUENCE_LENGTH) {
                    if (base == matrix[i+1][j+1] &&
                            base == matrix[i+2][j+2] &&
                            base == matrix[i+3][j+3]) {
                        sequencesFound++;
                        if (sequencesFound > 1) return true;
                    }
                }

                if (i <= n - SEQUENCE_LENGTH && j >= SEQUENCE_LENGTH - 1) {
                    if (base == matrix[i+1][j-1] &&
                            base == matrix[i+2][j-2] &&
                            base == matrix[i+3][j-3]) {
                        sequencesFound++;
                        if (sequencesFound > 1) return true;
                    }
                }
            }
        }

        return false;
    }
}