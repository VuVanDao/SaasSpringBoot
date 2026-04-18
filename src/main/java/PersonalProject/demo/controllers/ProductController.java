package PersonalProject.demo.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import PersonalProject.demo.Dto.Request.CreateProductRequest;
import PersonalProject.demo.Dto.Request.UpdateProductRequest;
import PersonalProject.demo.Dto.Response.ApiResponse;
import PersonalProject.demo.Dto.Response.ProductDto;
import PersonalProject.demo.services.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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
    // post: /products : create new product
    @PostMapping
    public ApiResponse<ProductDto> createProduct(@RequestBody CreateProductRequest request, HttpServletRequest request2) {
        return ApiResponse.<ProductDto>builder()
                .message("Product created successfully")
                .result(productService.createProduct(request,request2))
                .build();
    }
    
    // get: /products : get all products
    @GetMapping
    public ApiResponse<List<ProductDto>> getAllProduct(HttpServletRequest request2) {
        return ApiResponse.<List<ProductDto>>builder()
                .message("Products retrieved successfully")
                .result(productService.getAllProducts(request2))
                .build();
    }
    
    // get: /products/{id} : get product by id
    @GetMapping("/{id}")
    public ApiResponse<ProductDto> getProductById(@PathVariable Long id, HttpServletRequest request2) {
        return ApiResponse.<ProductDto>builder()
                .message("Product retrieved successfully")
                .result(productService.getProductById(id,request2))
                .build();
    }
    // put: /products/{id} : update product by id
    @PutMapping("/{id}")
    public ApiResponse<ProductDto> updateProduct(@PathVariable Long id, @RequestBody UpdateProductRequest request, HttpServletRequest request2) {
        return ApiResponse.<ProductDto>builder()
                .message("Product updated successfully")
                .result(productService.updateProduct(id, request, request2))
                .build();
    }
    // delete: /products/{id} : delete product by id
    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteProduct(@PathVariable Long id, HttpServletRequest request2) {
        productService.deleteProduct(id, request2);
        return ApiResponse.<Void>builder()
                .message("Product deleted successfully")
                .build();
    }
    // get: /products/store/{storeId} : search all products by store id
    @GetMapping("/store/{storeId}")
    public ApiResponse<List<ProductDto>> getAllProductsByStoreId(@PathVariable Long storeId, HttpServletRequest request2) {
        return ApiResponse.<List<ProductDto>>builder()
                .message("Get all product by storeID complete")
                .result(productService.getAllProductsByStoreId(storeId,request2))
                .build();
    }
    
    // get: /products/store/{storeId}/search?{query} : search all products by name, brand, category
    @GetMapping("/store/{storeId}/search")
    public ApiResponse<List<ProductDto>> getProductsByQuery(@PathVariable Long storeId,
            @RequestParam(required = false) String query,HttpServletRequest request2) {
        if(query == null || query.isEmpty()) {
            return ApiResponse.<List<ProductDto>>builder()
                    .message("Products retrieved successfully")
                    .result(productService.getAllProductsByStoreId(storeId,request2))
                    .build();
        }
        return ApiResponse.<List<ProductDto>>builder()
                .message("Products retrieved successfully")
                .result(productService.searchProducts(storeId, query))
                .build();
    }
}
