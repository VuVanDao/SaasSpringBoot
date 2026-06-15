package PersonalProject.demo.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import PersonalProject.demo.Dto.Request.CreateCategoryRequest;
import PersonalProject.demo.Dto.Response.ApiResponse;
import PersonalProject.demo.Dto.Response.CategoryResponse;
import PersonalProject.demo.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping
    public ApiResponse<CategoryResponse> createNewCategory(
            @RequestBody CreateCategoryRequest request,
            @RequestHeader("${app.header-tenant}") Long tenantId) {
        CategoryResponse categoryResponse = categoryService.createCategory(request, tenantId);
        return ApiResponse.<CategoryResponse>builder()
            .result(categoryResponse)
            .message("Category created successfully")
            .build();
    }

    @GetMapping
    public ApiResponse<List<CategoryResponse>> getAllCate(
            @RequestHeader("${app.header-tenant}") Long tenantId) {
        return ApiResponse.<List<CategoryResponse>>builder()
            .result(categoryService.getAllCategories(tenantId))
            .message("Categories retrieved successfully")
            .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<CategoryResponse> getCategoryById(
            @PathVariable Long id,
            @RequestHeader("${app.header-tenant}") Long tenantId) {
        CategoryResponse categoryResponse = categoryService.getCategoryById(id, tenantId);
        return ApiResponse.<CategoryResponse>builder()
                .result(categoryResponse)
                .message("Category retrieved successfully")
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteCategoryById(
            @PathVariable Long id,
            @RequestHeader("${app.header-tenant}") Long tenantId) {
        categoryService.deleteCategoryById(id, tenantId);
        return ApiResponse.<Void>builder()
                .message("Category deleted successfully")
                .build();
    }
}
