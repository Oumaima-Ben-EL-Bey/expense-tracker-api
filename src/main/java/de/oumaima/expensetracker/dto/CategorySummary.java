package de.oumaima.expensetracker.dto;

import java.math.BigDecimal;

public record CategorySummary(Long categoryId, String categoryName, BigDecimal total)
{}