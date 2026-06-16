package PersonalProject.demo.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ApiResponse<CategoryResponse>> createNewCategory(
            @RequestBody CreateCategoryRequest request,
            @RequestHeader("${app.header-tenant}") Long tenantId) {
        CategoryResponse categoryResponse = categoryService.createCategory(request, tenantId);
        ApiResponse<CategoryResponse> response = ApiResponse.<CategoryResponse>builder()
            .code(HttpStatus.CREATED.value())
            .result(categoryResponse)
            .message("Category created successfully")
            .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CategoryResponse>>> getAllCate(
            @RequestHeader("${app.header-tenant}") Long tenantId) {
        ApiResponse<List<CategoryResponse>> response = ApiResponse.<List<CategoryResponse>>builder()
            .code(HttpStatus.OK.value())
            .result(categoryService.getAllCategories(tenantId))
            .message("Categories retrieved successfully")
            .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryResponse>> getCategoryById(
            @PathVariable Long id,
            @RequestHeader("${app.header-tenant}") Long tenantId) {
        CategoryResponse categoryResponse = categoryService.getCategoryById(id, tenantId);
        ApiResponse<CategoryResponse> response = ApiResponse.<CategoryResponse>builder()
                .code(HttpStatus.OK.value())
                .result(categoryResponse)
                .message("Category retrieved successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCategoryById(
            @PathVariable Long id,
            @RequestHeader("${app.header-tenant}") Long tenantId) {
        categoryService.deleteCategoryById(id, tenantId);
        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .code(HttpStatus.OK.value())
                .message("Category deleted successfully")
                .build();
        return ResponseEntity.ok(response);
    }
}
