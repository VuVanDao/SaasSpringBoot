package PersonalProject.demo.services;

import java.util.List;

import PersonalProject.demo.Dto.Response.CategoryResponse;

public interface CategoryService {
    CategoryResponse createCategory(String name);
    List<CategoryResponse> getAllCategories();

    CategoryResponse getCategoryById(Long id);
    void deleteCategoryById(Long id);
}
