package org.example.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidDnaSequenceValidator implements ConstraintValidator<ValidDnaSequence, String[]> {

    @Override
    public boolean isValid(String[] dna, ConstraintValidatorContext context) {
        if (dna == null) return true; // @NotNull maneja el null

        int n = dna.length;
        if (n == 0) return false;

        for (String row : dna) {
            if (row == null || row.length() != n) return false; // Valida NxN
            if (!row.matches("[ATCG]+")) return false; // Valida letras
        }
        return true;
    }
}