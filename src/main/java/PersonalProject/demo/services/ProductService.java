package PersonalProject.demo.services;

import PersonalProject.demo.Dto.Request.CreateProductRequest;
import PersonalProject.demo.Dto.Request.UpdateProductRequest;
import PersonalProject.demo.Dto.Response.ProductDto;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;

public interface ProductService {

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_SUPER_ADMIN', 'ROLE_STORE_MANAGER')")
    ProductDto createProduct(CreateProductRequest request);

    List<ProductDto> getAllProducts();

    ProductDto getProductById(Long id);

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_SUPER_ADMIN', 'ROLE_STORE_MANAGER')")
    ProductDto updateProduct(Long id, UpdateProductRequest request);

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_SUPER_ADMIN', 'ROLE_STORE_MANAGER')")
    void deleteProduct(Long id);

    List<ProductDto> getProductsByStoreId(Long storeId);

    List<ProductDto> searchProducts(Long storeId, String query);
}
