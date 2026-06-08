package de.oumaima.expensetracker.expense;


import de.oumaima.expensetracker.dto.CategorySummary;
import de.oumaima.expensetracker.dto.ExpenseRequest;
import de.oumaima.expensetracker.dto.ExpenseResponse;
import de.oumaima.expensetracker.mapper.ExpenseMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Tag(name = "Expenses", description = "Create, read, update, delete, and summarize expenses")
@RestController
@RequestMapping("/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;
    private final ExpenseMapper expenseMapper;

    public ExpenseController(ExpenseService expenseService, ExpenseMapper expenseMapper) {
        this.expenseService = expenseService;
        this.expenseMapper = expenseMapper;
    }
    @Operation(summary = "List expenses",
            description = "Returns a paginated list of expenses, optionally filtered by category and date range.")
    @GetMapping
    public Page<ExpenseResponse> getAll(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) @DateTimeFormat(iso =
                    DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso =
                    DateTimeFormat.ISO.DATE) LocalDate endDate,
            Pageable pageable) {
        return expenseService.findAll(categoryId, startDate, endDate, pageable)
                .map(expenseMapper::toResponse);
    }
    @Operation(summary = "Get an expense by id")
    @GetMapping("/{id}")
    public ExpenseResponse getById(@PathVariable Long id) {
        return expenseService.findById(id).map(expenseMapper::toResponse).orElseThrow(()-> new ExpenseNotFoundException(id)) ;
    }

    @Operation(summary = "Create an expense")
    @PostMapping
    public ResponseEntity<ExpenseResponse> create(@Valid @RequestBody ExpenseRequest expense) {
        return  ResponseEntity.status(HttpStatus.CREATED).body(expenseMapper.toResponse(expenseService.create(expenseMapper.toEntity(expense))));
    }
    @Operation(summary = "Update an existing expense")
    @PutMapping("/{id}")
    public ExpenseResponse update(@PathVariable Long id, @Valid @RequestBody ExpenseRequest expense) {
        return expenseMapper.toResponse(expenseService.update(id, expenseMapper.toEntity(expense)));
    }

    @Operation(summary = "Delete an expense")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        expenseService.deleteById(id);
    }

    @Operation(summary = "Summarize expenses by category",
            description = "Calculates the sum of the amounts of each expense under the same category.")
    @GetMapping("/summary")
    public List<CategorySummary> summary() {
        return expenseService.summarizeByCategory();
    }
}
