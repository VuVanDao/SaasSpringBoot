package PersonalProject.demo.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import PersonalProject.demo.Dto.Request.CreateCategoryRequest;
import PersonalProject.demo.Dto.Response.ApiResponse;
import PersonalProject.demo.Dto.Response.CategoryResponse;
import PersonalProject.demo.services.CategoryService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping
    public ApiResponse<CategoryResponse> createNewCategory(@RequestBody CreateCategoryRequest request,HttpServletRequest request2) {
        CategoryResponse categoryResponse = categoryService.createCategory(request, request2);
        return ApiResponse.<CategoryResponse>builder()
            .result(categoryResponse)
            .message("Category created successfully")
            .build();
    }
    @GetMapping
    public ApiResponse<List<CategoryResponse>> getAllCate(HttpServletRequest request) {
        return ApiResponse.<List<CategoryResponse>>builder()
            .result(categoryService.getAllCategories(request))
            .message("Categories retrieved successfully")
            .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<CategoryResponse> getCategoryById(@PathVariable Long id, HttpServletRequest request) {
        CategoryResponse categoryResponse = categoryService.getCategoryById(id,request);
        return ApiResponse.<CategoryResponse>builder()
                .result(categoryResponse)
                .message("Category retrieved successfully")
                .build();
    }
    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteCategoryById(@PathVariable Long id, HttpServletRequest request) {
        categoryService.deleteCategoryById(id, request);
        return ApiResponse.<Void>builder()
                .message("Category deleted successfully")
                .build();
    }
    
}
