package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.dto.DnaRequest;
import org.example.service.MutantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@Tag(name = "Mutant Detector", description = "API para detectar mutantes")
@RequiredArgsConstructor
public class MutantController {

    private final MutantService service;

    @Operation(summary = "Analizar ADN", description = "Detecta si un humano es mutante basándose en su secuencia de ADN")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Es Mutante"),
            @ApiResponse(responseCode = "403", description = "Es Humano"),
            @ApiResponse(responseCode = "400", description = "ADN Inválido (formato incorrecto)")
    })
    @PostMapping("/mutant")
    public ResponseEntity<Void> isMutant(@Valid @RequestBody DnaRequest request) {
        boolean isMutant = service.analyze(request.getDna());
        return isMutant ? ResponseEntity.ok().build() : ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @Operation(summary = "Health Check", description = "Verifica que la API esté viva y funcionando correctamente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "API operativa")
    })
    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> healthCheck() {
        return ResponseEntity.ok(Map.of(
                "status", "UP",
                "message", "Mutant Detector API is running smoothly!",
                "timestamp", LocalDateTime.now()
        ));
    }
}