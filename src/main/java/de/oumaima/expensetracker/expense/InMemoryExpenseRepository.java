package de.oumaima.expensetracker.expense;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class InMemoryExpenseRepository implements ExpenseRepository {
    private final Map<Long, Expense> expenses = new HashMap<>();
    private long nextId = 1;

    @Override
    public List<Expense> findAll() {
        return new ArrayList<>(expenses.values());
    }

    @Override
    public Optional<Expense> findById(Long id){
        return Optional.ofNullable(expenses.get(id));

    }

    @Override
    public Expense save(Expense expense) {
        if (expense.getId() == null) {
            Long assignedId = nextId++;
            Expense createdExpense = new Expense(assignedId,
                    expense.getDescription(),expense.getAmount(),expense.getDate());
            expenses.put( assignedId,createdExpense );
            return createdExpense;

        } else {
            // UPDATE branch: store at the existing id, return the expense
            expenses.put(expense.getId(), expense);
            return expense;
        }
    }

    @Override
    public void deleteById(Long id) {
        expenses.remove(id);
    }
}

