package org.example.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import org.example.validation.ValidDnaSequence;

import java.io.Serializable;

@Schema(description = "DTO para recibir la secuencia de ADN")
public class DnaRequest implements Serializable {

    @Schema(example = "[\"ATGCGA\",\"CAGTGC\",\"TTATGT\",\"AGAAGG\",\"CCCCTA\",\"TCACTG\"]")
    @NotNull(message = "El ADN no puede ser nulo")
    @ValidDnaSequence
    @JsonProperty("dna")
    private String[] dna;

    public String[] getDna() {
        return dna;
    }

    public void setDna(String[] dna) {
        this.dna = dna;
    }
}