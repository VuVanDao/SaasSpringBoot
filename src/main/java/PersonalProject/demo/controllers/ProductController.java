package PersonalProject.demo.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import PersonalProject.demo.Dto.Request.CreateProductRequest;
import PersonalProject.demo.Dto.Request.UpdateProductRequest;
import PersonalProject.demo.Dto.Response.ApiResponse;
import PersonalProject.demo.Dto.Response.ProductDto;
import PersonalProject.demo.services.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping
    public ApiResponse<ProductDto> createProduct(
            @RequestBody CreateProductRequest request,
            @RequestHeader("${app.header-tenant}") Long tenantId) {
        return ApiResponse.<ProductDto>builder()
                .message("Product created successfully")
                .result(productService.createProduct(request, tenantId))
                .build();
    }
    
    @GetMapping
    public ApiResponse<List<ProductDto>> getAllProduct(
            @RequestHeader("${app.header-tenant}") Long tenantId) {
        return ApiResponse.<List<ProductDto>>builder()
                .message("Products retrieved successfully")
                .result(productService.getAllProducts(tenantId))
                .build();
    }
    
    @GetMapping("/{id}")
    public ApiResponse<ProductDto> getProductById(
            @PathVariable Long id,
            @RequestHeader("${app.header-tenant}") Long tenantId) {
        return ApiResponse.<ProductDto>builder()
                .message("Product retrieved successfully")
                .result(productService.getProductById(id, tenantId))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<ProductDto> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody UpdateProductRequest request,
            @RequestHeader("${app.header-tenant}") Long tenantId) {
        return ApiResponse.<ProductDto>builder()
                .message("Product updated successfully")
                .result(productService.updateProduct(id, request, tenantId))
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteProduct(
            @PathVariable Long id,
            @RequestHeader("${app.header-tenant}") Long tenantId) {
        productService.deleteProduct(id, tenantId);
        return ApiResponse.<Void>builder()
                .message("Product deleted successfully")
                .build();
    }

    @GetMapping("/store/{storeId}")
    public ApiResponse<List<ProductDto>> getAllProductsByStoreId(
            @PathVariable Long storeId,
            @RequestHeader("${app.header-tenant}") Long tenantId) {
        return ApiResponse.<List<ProductDto>>builder()
                .message("Get all product by storeID complete")
                .result(productService.getAllProductsByStoreId(storeId, tenantId))
                .build();
    }
    
    @GetMapping("/store/{storeId}/search")
    public ApiResponse<List<ProductDto>> getProductsByQuery(
            @PathVariable Long storeId,
            @RequestParam(required = false) String query,
            @RequestHeader("${app.header-tenant}") Long tenantId) {
        if(query == null || query.isEmpty()) {
            return ApiResponse.<List<ProductDto>>builder()
                    .message("Products retrieved successfully")
                    .result(productService.getAllProductsByStoreId(storeId, tenantId))
                    .build();
        }
        return ApiResponse.<List<ProductDto>>builder()
                .message("Products retrieved successfully")
                .result(productService.searchProducts(storeId, query))
                .build();
    }
}
