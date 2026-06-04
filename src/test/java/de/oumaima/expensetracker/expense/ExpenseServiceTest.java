package de.oumaima.expensetracker.expense;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class ExpenseServiceTest {


    @Mock
    private ExpenseRepository repo;

    @InjectMocks
    private ExpenseService service;

    @BeforeEach
    void setUp() {
        repo = mock(ExpenseRepository.class);
        service = new ExpenseService(repo);
    }

    @Test
    void update_throwsWhenExpenseNotFound() {
        // Arrange: a fake repository that finds nothing for id 99
        when(repo.findById(99L)).thenReturn(Optional.empty());


        // Act + Assert: calling update on a missing id must throw
        assertThrows(
                ExpenseNotFoundException.class,
                () -> service.update(99L, new Expense())
        );
    }

    @Test
    void update_appliesNewValuesIncludingCategory() {
        // Arrange — the expense as it already exists (OLD values, OLD category)
        Category oldCategory = new Category("Food");
        Expense existing = new Expense();
        existing.setDescription("old description");
        existing.setAmount(new BigDecimal("5.00"));
        existing.setDate(LocalDate.of(2026, 1, 1));
        existing.setCategory(oldCategory);

        // Arrange — the incoming update (NEW values, NEW category)
        Category newCategory = new Category("Books");
        Expense input = new Expense();
        input.setDescription("new description");
        input.setAmount(new BigDecimal("12.50"));
        input.setDate(LocalDate.of(2026, 6, 1));
        input.setCategory(newCategory);

        when(repo.findById(2L)).thenReturn(Optional.of(existing));

        // Act
        Expense result = service.update(2L, input);

        // Assert — every field now holds the new value, category included
        assertEquals("new description", result.getDescription());
        assertEquals(new BigDecimal("12.50"), result.getAmount());
        assertEquals(LocalDate.of(2026, 6, 1), result.getDate());
        assertEquals("Books", result.getCategory().getName());
    }

    @Test
    void deleteById_throwsWhenNotFound() {
        when(repo.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ExpenseNotFoundException.class, () -> service.deleteById(99L));
        verify(repo, never()).deleteById(any());
    }

    @Test
    void deleteById_deletesWhenFound() {
        when(repo.findById(7L)).thenReturn(Optional.of(new Expense()));

        service.deleteById(7L);

        verify(repo).deleteById(7L);
    }


}