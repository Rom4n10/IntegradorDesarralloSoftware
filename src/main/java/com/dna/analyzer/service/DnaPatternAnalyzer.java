package com.dna.analyzer.service;

import org.springframework.stereotype.Component;
import java.util.Set;

@Component
public class DnaPatternAnalyzer {

    private static final int ANOMALY_THRESHOLD = 4;
    private static final Set<Character> ALLOWED_BASES = Set.of('A', 'T', 'C', 'G');

    public boolean detectAnomaly(String[] dnaSequence) {
        if (dnaSequence == null || dnaSequence.length == 0) return false;

        int n = dnaSequence.length;
        if (n < ANOMALY_THRESHOLD) return false;

        char[][] dnaGrid = new char[n][];

        // Validate and build grid
        for (int i = 0; i < n; i++) {
            if (dnaSequence[i] == null || dnaSequence[i].length() != n) {
                return false;
            }
            for (char base : dnaSequence[i].toCharArray()) {
                if (!ALLOWED_BASES.contains(base)) {
                    return false;
                }
            }
            dnaGrid[i] = dnaSequence[i].toCharArray();
        }

        int anomaliesDetected = 0;

        // Check Vertical
        anomaliesDetected += checkVertical(dnaGrid, n);
        if (anomaliesDetected > 1) return true;

        // Check Horizontal
        anomaliesDetected += checkHorizontal(dnaGrid, n);
        if (anomaliesDetected > 1) return true;

        // Check Diagonals
        anomaliesDetected += checkDiagonals(dnaGrid, n);
        
        return anomaliesDetected > 1;
    }

    private int checkVertical(char[][] grid, int n) {
        int count = 0;
        for (int j = 0; j < n; j++) {
            for (int i = 0; i <= n - ANOMALY_THRESHOLD; i++) {
                if (checkLine(grid[i][j], grid[i+1][j], grid[i+2][j], grid[i+3][j])) {
                    count++;
                    if (count > 1) return count;
                }
            }
        }
        return count;
    }

    private int checkHorizontal(char[][] grid, int n) {
        int count = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j <= n - ANOMALY_THRESHOLD; j++) {
                if (checkLine(grid[i][j], grid[i][j+1], grid[i][j+2], grid[i][j+3])) {
                    count++;
                    if (count > 1) return count;
                }
            }
        }
        return count;
    }

    private int checkDiagonals(char[][] grid, int n) {
        int count = 0;
        // Main Diagonal
        for (int i = 0; i <= n - ANOMALY_THRESHOLD; i++) {
            for (int j = 0; j <= n - ANOMALY_THRESHOLD; j++) {
                if (checkLine(grid[i][j], grid[i+1][j+1], grid[i+2][j+2], grid[i+3][j+3])) {
                    count++;
                    if (count > 1) return count;
                }
            }
        }
        
        // Anti-Diagonal
        for (int i = 0; i <= n - ANOMALY_THRESHOLD; i++) {
            for (int j = ANOMALY_THRESHOLD - 1; j < n; j++) {
                if (checkLine(grid[i][j], grid[i+1][j-1], grid[i+2][j-2], grid[i+3][j-3])) {
                    count++;
                    if (count > 1) return count;
                }
            }
        }
        return count;
    }

    private boolean checkLine(char c1, char c2, char c3, char c4) {
        return c1 == c2 && c1 == c3 && c1 == c4;
    }
}