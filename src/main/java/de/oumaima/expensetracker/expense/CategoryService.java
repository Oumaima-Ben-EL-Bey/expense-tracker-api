package de.oumaima.expensetracker.expense;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category create(Category category) {
        return categoryRepository.save(category);
    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Optional<Category> findById(Long id) {
        return categoryRepository.findById(id);
    }

    public void deleteById(Long id) {

        categoryRepository.deleteById(id);
    }

    @Transactional
    public Category update(Long id, Category category) {
        Optional<Category> found = categoryRepository.findById(id);
        if(found.isEmpty()) {
            throw new CategoryNotFoundException(id);
        }
        Category categoryCaptured = found.get();
        categoryCaptured.setName(category.getName());
        return categoryCaptured;
    }
}
