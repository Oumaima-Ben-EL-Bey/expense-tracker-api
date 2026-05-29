package de.oumaima.expensetracker.expense;

import org.springframework.web.bind.annotation.ResponseStatus;

public record ErrorResponse(int status, String message) {
}

