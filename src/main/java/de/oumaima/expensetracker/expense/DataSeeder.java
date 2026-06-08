package de.oumaima.expensetracker.expense;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
public class DataSeeder implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final ExpenseRepository expenseRepository;

    public DataSeeder(CategoryRepository categoryRepository,
                      ExpenseRepository expenseRepository) {
        this.categoryRepository = categoryRepository;
        this.expenseRepository = expenseRepository;
    }

    @Override
    public void run(String... args) {
        if (categoryRepository.count() > 0) {
            return;   // already seeded — do nothing. This is the idempotency guard.
        }

        Category transport = categoryRepository.save(new Category("Transport"));
        Category leisure   = categoryRepository.save(new Category("Leisure"));
        Category food      = categoryRepository.save(new Category("Food"));


        Expense meals = new Expense();
        meals.setDescription("Weekly grocery run");
        meals.setAmount(new BigDecimal("42.30"));
        meals.setDate(LocalDate.of(2026, 4, 12));
        meals.setCategory(food);
        expenseRepository.save(meals);

        Expense lunch = new Expense();
        lunch.setDescription("Lunch with friends");
        lunch.setAmount(new BigDecimal("32.80"));
        lunch.setDate(LocalDate.of(2026, 5, 2));
        lunch.setCategory(food);
        expenseRepository.save(lunch);

        Expense travel = new Expense();
        travel.setDescription("Train to Cologne");
        travel.setAmount(new BigDecimal("22.30"));
        travel.setDate(LocalDate.of(2026, 5, 10));
        travel.setCategory(transport);
        expenseRepository.save(travel);

        Expense fun = new Expense();
        fun.setDescription("Phantasialand ticket");
        fun.setAmount(new BigDecimal("27.00"));
        fun.setDate(LocalDate.of(2026, 6, 1));
        fun.setCategory(leisure);
        expenseRepository.save(fun);




    }
}