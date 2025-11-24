package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.example.dto.StatsResponse;
import org.example.service.StatsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stats")
@RequiredArgsConstructor
public class StatsController {

    private final StatsService service;

    @Operation(summary = "Obtener estadísticas")
    @GetMapping
    public ResponseEntity<StatsResponse> getStats() {
        return ResponseEntity.ok(service.getStats());
    }
}