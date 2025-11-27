package com.dna.analyzer.service;

import lombok.RequiredArgsConstructor;
import com.dna.analyzer.entity.AnalyzedDna;
import com.dna.analyzer.repository.AnalyzedDnaRepository;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DnaAnalysisService {

    private final AnalyzedDnaRepository repository;
    private final DnaPatternAnalyzer patternAnalyzer;
    
    // Salt to ensure unique hashes compared to other implementations
    private static final String SALT = "[ROMAN_MOLINA_51202]";

    public boolean processDna(String[] dna) {
        String uniqueHash = generateUniqueHash(dna);

        Optional<AnalyzedDna> existing = repository.findByDnaHash(uniqueHash);
        if (existing.isPresent()) {
            return existing.get().isMutant();
        }

        boolean isAnomaly = patternAnalyzer.detectAnomaly(dna);

        AnalyzedDna record = AnalyzedDna.builder()
                .dnaHash(uniqueHash)
                .sequence(String.join(",", dna))
                .isMutant(isAnomaly)
                .build();

        repository.save(record);

        return isAnomaly;
    }

    private String generateUniqueHash(String[] dna) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            StringBuilder sb = new StringBuilder();
            sb.append(SALT); // Add salt first
            for (String s : dna) {
                sb.append(s);
            }
            byte[] encodedHash = digest.digest(sb.toString().getBytes(StandardCharsets.UTF_8));

            StringBuilder hexString = new StringBuilder(2 * encodedHash.length);
            for (byte b : encodedHash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error calculating unique DNA hash", e);
        }
    }
}