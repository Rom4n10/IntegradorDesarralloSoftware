package com.dna.analyzer.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import com.dna.analyzer.dto.DnaRequest;
import com.dna.analyzer.service.DnaAnalysisService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@Tag(name = "DNA Analyzer", description = "API para analizar secuencias de ADN y detectar anomalías")
@RequiredArgsConstructor
public class AnalysisController {

    private final DnaAnalysisService service;

    @Operation(summary = "Analizar ADN", description = "Detecta si una secuencia de ADN presenta anomalías (Mutante)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Es Mutante (Anomalía detectada)"),
            @ApiResponse(responseCode = "403", description = "Es Humano (Sin anomalías)"),
            @ApiResponse(responseCode = "400", description = "ADN Inválido (formato incorrecto)")
    })
    @PostMapping("/mutant")
    public ResponseEntity<Void> analyzeDna(@Valid @RequestBody DnaRequest request) {
        boolean isAnomaly = service.processDna(request.getDna());
        return isAnomaly ? ResponseEntity.ok().build() : ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @Operation(summary = "Health Check", description = "Verifica que la API esté viva y funcionando correctamente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "API operativa")
    })
    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> healthCheck() {
        return ResponseEntity.ok(Map.of(
                "status", "UP",
                "message", "DNA Analyzer API is running smoothly!",
                "timestamp", LocalDateTime.now()
        ));
    }
}