package de.oumaima.expensetracker.expense;

import org.aspectj.apache.bcel.classfile.Module;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<Category> create(@RequestBody Category category) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.create(category));
    }

    @GetMapping
    public List<Category> getAll() {
        return categoryService.findAll();
    }
    @GetMapping("/{id}")
    public Category getById(@PathVariable Long id) {
        return categoryService.findById(id).orElseThrow(() -> new CategoryNotFoundException(id));
    }

    @PutMapping({"/{id}"})
    public Category update(@PathVariable Long id, @RequestBody Category category) {
        return categoryService.update(id,category);
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        categoryService.deleteById(id);

    }
}