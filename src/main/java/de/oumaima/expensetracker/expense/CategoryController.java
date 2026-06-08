package de.oumaima.expensetracker.expense;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Tag(name = "Categories", description = "Create, read, update and delete categories")
@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }
    @Operation(summary = "Create a category")
    @PostMapping
    public ResponseEntity<Category> create(@RequestBody Category category) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.create(category));
    }
    @Operation(summary = "List categories",
            description = "Return a list of categories.")
    @GetMapping
    public List<Category> getAll() {
        return categoryService.findAll();
    }

    @Operation(summary = "Get a category by id")
    @GetMapping("/{id}")
    public Category getById(@PathVariable Long id) {
        return categoryService.findById(id).orElseThrow(() -> new CategoryNotFoundException(id));
    }
    @Operation(summary = "Update an existing category")
    @PutMapping({"/{id}"})
    public Category update(@PathVariable Long id, @RequestBody Category category) {
        return categoryService.update(id,category);
    }

    @Operation(summary = "Delete a category")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        categoryService.deleteById(id);

    }
}