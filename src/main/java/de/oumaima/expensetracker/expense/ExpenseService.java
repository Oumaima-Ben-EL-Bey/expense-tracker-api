package de.oumaima.expensetracker.expense;


import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExpenseService {
    private final ExpenseRepository expenseRepository;


    public ExpenseService(ExpenseRepository expenseRepository){
        this.expenseRepository = expenseRepository;
    }

    public List<Expense> findAll(){
        return expenseRepository.findAll();
    }

    public Optional<Expense> findById(Long id) {
        return expenseRepository.findById(id);
    }

    public Expense create(Expense expense) {
        return expenseRepository.save(expense);
    }

    public Expense update(Long id, Expense expense) {
        if (expenseRepository.findById(id).isEmpty()){
            throw new ExpenseNotFoundException(id);
        }
        Expense expenseUpdated = new Expense(id, expense.getDescription(), expense.getAmount(), expense.getDate());
        return expenseRepository.save(expenseUpdated);

    }
    public void deleteById(Long id) {
        if (expenseRepository.findById(id).isEmpty()){
            throw new ExpenseNotFoundException(id);
        }
        expenseRepository.deleteById(id);
    }
}
