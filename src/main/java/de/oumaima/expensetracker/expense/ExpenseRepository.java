package de.oumaima.expensetracker.expense;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {


    @Query("SELECT e FROM Expense e WHERE " +
            "(:categoryId IS NULL OR e.category.id = :categoryId) AND " +
            "(CAST(:startDate AS date) IS NULL OR e.date >= :startDate) AND " +
            "(CAST(:endDate AS date) IS NULL OR e.date <= :endDate)")
    List<Expense> findFiltered(@Param("categoryId") Long categoryId,
                               @Param("startDate") LocalDate startDate,
                               @Param("endDate") LocalDate endDate);
}
