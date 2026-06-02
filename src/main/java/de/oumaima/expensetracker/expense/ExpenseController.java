package de.oumaima.expensetracker.expense;


import de.oumaima.expensetracker.dto.ExpenseRequest;
import de.oumaima.expensetracker.dto.ExpenseResponse;
import de.oumaima.expensetracker.mapper.ExpenseMapper;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;
    private final ExpenseMapper expenseMapper;

    public ExpenseController(ExpenseService expenseService, ExpenseMapper expenseMapper) {
        this.expenseService = expenseService;
        this.expenseMapper = expenseMapper;
    }
    @GetMapping
    public List<ExpenseResponse> getAll(@RequestParam(required = false) Long categoryId,
                                        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return expenseService.findAll(categoryId,startDate, endDate)
                .stream()
                .map(expenseMapper::toResponse)
                .toList();
    }

    @GetMapping("/{id}")
    public ExpenseResponse getById(@PathVariable Long id) {
        return expenseService.findById(id).map(expenseMapper::toResponse).orElseThrow(()-> new ExpenseNotFoundException(id)) ;
    }

    @PostMapping
    public ResponseEntity<ExpenseResponse> create(@Valid @RequestBody ExpenseRequest expense) {
        return  ResponseEntity.status(HttpStatus.CREATED).body(expenseMapper.toResponse(expenseService.create(expenseMapper.toEntity(expense))));
    }

    @PutMapping("/{id}")
    public ExpenseResponse update(@PathVariable Long id, @Valid @RequestBody ExpenseRequest expense) {
        return expenseMapper.toResponse(expenseService.update(id, expenseMapper.toEntity(expense)));
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        expenseService.deleteById(id);
    }
}
