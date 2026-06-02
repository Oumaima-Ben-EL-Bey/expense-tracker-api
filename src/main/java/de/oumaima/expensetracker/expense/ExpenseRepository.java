package de.oumaima.expensetracker.expense;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {


    @Query(value = "SELECT e FROM Expense e " +
            "LEFT JOIN FETCH e.category " +
            "WHERE (:categoryId IS NULL OR e.category.id = :categoryId) AND " +
            "(CAST(:startDate AS date) IS NULL OR e.date >= :startDate) AND " +
            "(CAST(:endDate AS date) IS NULL OR e.date <= :endDate)",
            countQuery = "SELECT COUNT(e) FROM Expense e " +
                    "WHERE (:categoryId IS NULL OR e.category.id = :categoryId) AND " +
                    "(CAST(:startDate AS date) IS NULL OR e.date >= :startDate) AND " +
                    "(CAST(:endDate AS date) IS NULL OR e.date <= :endDate)")
    Page<Expense> findFiltered(@Param("categoryId") Long categoryId,
                               @Param("startDate") LocalDate startDate,
                               @Param("endDate") LocalDate endDate,
                               Pageable pageable);
}
