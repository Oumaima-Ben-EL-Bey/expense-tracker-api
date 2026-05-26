package de.oumaima.expensetracker.expense;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataExpenseRepository
        extends ExpenseRepository, JpaRepository<Expense, Long> {
}
