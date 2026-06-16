package PersonalProject.demo.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@RequestMapping
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping("/products")
    public ResponseEntity<ApiResponse<ProductDto>> createProduct(
            @RequestBody CreateProductRequest request,
            @RequestHeader("${app.header-tenant}") Long tenantId) {
        ApiResponse<ProductDto> response = ApiResponse.<ProductDto>builder()
                .code(HttpStatus.CREATED.value())
                .message("Product created successfully")
                .result(productService.createProduct(request, tenantId))
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @GetMapping("/products")
    public ResponseEntity<ApiResponse<List<ProductDto>>> getAllProduct(
            @RequestHeader("${app.header-tenant}") Long tenantId) {
        ApiResponse<List<ProductDto>> response = ApiResponse.<List<ProductDto>>builder()
                .code(HttpStatus.OK.value())
                .message("Products retrieved successfully")
                .result(productService.getAllProducts(tenantId))
                .build();
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/products/{id}")
    public ResponseEntity<ApiResponse<ProductDto>> getProductById(
            @PathVariable Long id,
            @RequestHeader("${app.header-tenant}") Long tenantId) {
        ApiResponse<ProductDto> response = ApiResponse.<ProductDto>builder()
                .code(HttpStatus.OK.value())
                .message("Product retrieved successfully")
                .result(productService.getProductById(id, tenantId))
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<ApiResponse<ProductDto>> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody UpdateProductRequest request,
            @RequestHeader("${app.header-tenant}") Long tenantId) {
        ApiResponse<ProductDto> response = ApiResponse.<ProductDto>builder()
                .code(HttpStatus.OK.value())
                .message("Product updated successfully")
                .result(productService.updateProduct(id, request, tenantId))
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteProduct(
            @PathVariable Long id,
            @RequestHeader("${app.header-tenant}") Long tenantId) {
        productService.deleteProduct(id, tenantId);
        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .code(HttpStatus.OK.value())
                .message("Product deleted successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/stores/{storeId}/products")
    public ResponseEntity<ApiResponse<List<ProductDto>>> getProductsByStoreId(
            @PathVariable Long storeId,
            @RequestParam(required = false) String query,
            @RequestHeader("${app.header-tenant}") Long tenantId) {
        List<ProductDto> products;
        if (query == null || query.isEmpty()) {
            products = productService.getAllProductsByStoreId(storeId, tenantId);
        } else {
            products = productService.searchProducts(storeId, query);
        }
        ApiResponse<List<ProductDto>> response = ApiResponse.<List<ProductDto>>builder()
                .code(HttpStatus.OK.value())
                .message("Products retrieved successfully")
                .result(products)
                .build();
        return ResponseEntity.ok(response);
    }
}
