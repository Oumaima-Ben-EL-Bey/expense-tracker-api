package de.oumaima.expensetracker.mapper;

import de.oumaima.expensetracker.dto.CategoryResponse;
import de.oumaima.expensetracker.dto.ExpenseRequest;
import de.oumaima.expensetracker.dto.ExpenseResponse;
import de.oumaima.expensetracker.expense.Category;
import de.oumaima.expensetracker.expense.CategoryNotFoundException;
import de.oumaima.expensetracker.expense.CategoryRepository;
import de.oumaima.expensetracker.expense.Expense;
import org.springframework.stereotype.Component;

@Component
public class ExpenseMapper {
    private final CategoryRepository categoryRepository;

    public ExpenseMapper(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Expense toEntity(ExpenseRequest request) {
        Long categoryId = request.categoryId();
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException(categoryId));

        Expense expense = new Expense();
        expense.setDescription(request.description());
        expense.setAmount(request.amount());
        expense.setDate(request.date());
        expense.setCategory(category);
        return expense;
    }
    public ExpenseResponse toResponse(Expense expense) {
        CategoryResponse category = null;
        if (expense.getCategory() != null) {
            category = new CategoryResponse(
                    expense.getCategory().getId(),
                    expense.getCategory().getName()
            );
        }

        return new ExpenseResponse(
                expense.getId(),
                expense.getDescription(),
                expense.getAmount(),
                expense.getDate(),
                category
        );
    }



}