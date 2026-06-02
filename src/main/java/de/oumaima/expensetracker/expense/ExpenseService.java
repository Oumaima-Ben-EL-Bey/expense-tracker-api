package de.oumaima.expensetracker.expense;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ExpenseService {
    private final ExpenseRepository expenseRepository;


    public ExpenseService(ExpenseRepository expenseRepository){
        this.expenseRepository = expenseRepository;
    }

    public List<Expense> findAll(Long categoryId,  LocalDate startDate,  LocalDate endDate){
        return expenseRepository.findFiltered(categoryId, startDate, endDate);

    }

    public Optional<Expense> findById(Long id) {
        return expenseRepository.findById(id);
    }

    public Expense create(Expense expense) {
        return expenseRepository.save(expense);
    }

    @Transactional
    public Expense update(Long id, Expense expense) {
        Optional<Expense> found = expenseRepository.findById(id);
        if (found.isEmpty()){
            throw new ExpenseNotFoundException(id);
        }
        Expense expenseCaptured = found.get() ;
        expenseCaptured.setDescription(expense.getDescription());
        expenseCaptured.setDate(expense.getDate());
        expenseCaptured.setAmount(expense.getAmount());
        return expenseCaptured;

    }
    public void deleteById(Long id) {
        if (expenseRepository.findById(id).isEmpty()){
            throw new ExpenseNotFoundException(id);
        }
        expenseRepository.deleteById(id);
    }
}
