package PersonalProject.demo.services;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;

import PersonalProject.demo.Dto.Request.CreateCategoryRequest;
import PersonalProject.demo.Dto.Response.CategoryResponse;
import jakarta.servlet.http.HttpServletRequest;

public interface CategoryService {

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_SUPER_ADMIN', 'ROLE_STORE_MANAGER')")
    CategoryResponse createCategory(CreateCategoryRequest request, HttpServletRequest request2);

    List<CategoryResponse> getAllCategories(HttpServletRequest request);

    CategoryResponse getCategoryById(Long id);
    void deleteCategoryById(Long id);
}
