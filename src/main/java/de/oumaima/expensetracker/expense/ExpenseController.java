package de.oumaima.expensetracker.expense;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @GetMapping
    public List<Expense> getAll() {
        return expenseService.findAll();
    }

    @GetMapping("/{id}")
    public Expense getById(@PathVariable Long id) {
        return expenseService.findById(id).orElseThrow(()-> new ExpenseNotFoundException(id)) ;
    }
    @PostMapping
    public ResponseEntity<Expense> create(@RequestBody Expense expense) {
        return  ResponseEntity.status(HttpStatus.CREATED).body(expenseService.create(expense));
    }

    @PutMapping("/{id}")
    public Expense update(@PathVariable Long id,  @RequestBody Expense expense) {
        return expenseService.update(id, expense);
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        expenseService.deleteById(id);
    }
}
