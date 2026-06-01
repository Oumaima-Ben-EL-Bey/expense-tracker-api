package de.oumaima.expensetracker.dto;

import java.util.List;

public record ValidationErrorResponse(int status, List<ValidationError> errors) {
}