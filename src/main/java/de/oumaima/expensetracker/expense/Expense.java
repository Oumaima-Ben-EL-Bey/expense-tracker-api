package de.oumaima.expensetracker.expense;

import java.math.BigDecimal;
import java.time.LocalDate;

public record Expense(Long id, String description, BigDecimal amount, LocalDate date) {
}
