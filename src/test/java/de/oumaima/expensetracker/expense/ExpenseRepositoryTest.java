package de.oumaima.expensetracker.expense;

import de.oumaima.expensetracker.dto.CategorySummary;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers
class ExpenseRepositoryTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new
            PostgreSQLContainer<>("postgres:16");

    @Autowired
    private ExpenseRepository repository;

    @Autowired
    private CategoryRepository categoryRepository;



    @Test
    void savesAndFindsExpense() {
        Expense expense = new Expense();
        expense.setDescription("Test lunch");
        expense.setAmount(new BigDecimal("10.00"));
        expense.setDate(LocalDate.of(2026, 6, 1));

        Expense saved = repository.save(expense);

        assertTrue(repository.findById(saved.getId()).isPresent());
    }

    private Expense newExpense(String description, String amount, Category category)
    {
        Expense e = new Expense();
        e.setDescription(description);
        e.setAmount(new BigDecimal(amount));
        e.setDate(LocalDate.of(2026, 6, 1));
        e.setCategory(category);
        return e;
    }

    @Test
    void findFiltered_returnsOnlyExpensesInThatCategory() {
        Category food = categoryRepository.save(new Category("Food"));
        Category transport = categoryRepository.save(new Category("Transport"));

        repository.save(newExpense("Lunch", "10.00", food));
        repository.save(newExpense("Dinner", "20.00", food));
        repository.save(newExpense("Bus", "2.50", transport));

        Page<Expense> result =
                repository.findFiltered(food.getId(), null, null, PageRequest.of(0,
                        10));

        assertEquals(2, result.getTotalElements());
    }
    @Test
    void summarizeByCategory_totalsPerCategoryBiggestFirst() {
        Category food = categoryRepository.save(new Category("Food"));
        Category transport = categoryRepository.save(new Category("Transport"));

        repository.save(newExpense("Lunch", "10.00", food));
        repository.save(newExpense("Dinner", "20.00", food));
        repository.save(newExpense("Bus", "2.50", transport));
        repository.save(newExpense("Mystery", "5.00", null));

        List<CategorySummary> summary = repository.summarizeByCategory();

        assertEquals(2, summary.size());
        assertEquals("Food", summary.get(0).categoryName());
        assertEquals(0, new BigDecimal("30.00").compareTo(summary.get(0).total()));
        assertEquals("Transport", summary.get(1).categoryName());
        assertEquals(0, new BigDecimal("2.50").compareTo(summary.get(1).total()));
    }
}