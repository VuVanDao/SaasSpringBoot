package PersonalProject.demo.Implementation;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import PersonalProject.demo.Dto.Response.CategoryResponse;
import PersonalProject.demo.models.Category;
import PersonalProject.demo.repositories.CategoryRepositories;
import PersonalProject.demo.services.CategoryService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryImplementation implements CategoryService {
    private final CategoryRepositories categoryRepositories;
    
    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public CategoryResponse createCategory(String name) {
        Category newCategory = Category.builder().name(name).build();
        categoryRepositories.save(newCategory);
        return CategoryResponse.builder().name(name).build();
    }

    @Override
    public List<CategoryResponse> getAllCategories() {
        return categoryRepositories.findAll().stream()
            .map(category -> CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .build())
            .toList();
    }

    @Override
    public CategoryResponse getCategoryById(Long id) {
        return categoryRepositories.findById(id)
                .map(category -> CategoryResponse.builder()
                        .id(category.getId())
                        .name(category.getName())
                        .build())
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public void deleteCategoryById(Long id) {
        Category category = categoryRepositories.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));
        categoryRepositories.delete(category);
    }
    
}
