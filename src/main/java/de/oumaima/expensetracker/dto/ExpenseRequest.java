package de.oumaima.expensetracker.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ExpenseRequest(
        @NotBlank
        String description,
        @Positive
        @NotNull
        BigDecimal amount,
        @NotNull
        LocalDate date,
        @NotNull
        Long categoryId
) {}