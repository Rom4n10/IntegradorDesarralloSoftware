package com.dna.analyzer.repository;

import com.dna.analyzer.entity.AnalyzedDna;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface AnalyzedDnaRepository extends JpaRepository<AnalyzedDna, Long> {
    Optional<AnalyzedDna> findByDnaHash(String dnaHash);

    long countByIsMutant(boolean isMutant);
}