package PersonalProject.demo.services;

import PersonalProject.demo.Dto.Request.CreateProductRequest;
import PersonalProject.demo.Dto.Request.UpdateProductRequest;
import PersonalProject.demo.Dto.Response.ProductDto;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;

public interface ProductService {

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_SUPER_ADMIN', 'ROLE_STORE_MANAGER')")
    ProductDto createProduct(CreateProductRequest request, HttpServletRequest request2);

    List<ProductDto> getAllProducts(HttpServletRequest request2);

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_SUPER_ADMIN', 'ROLE_STORE_MANAGER')")
    ProductDto getProductById(Long id, HttpServletRequest request2);

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_SUPER_ADMIN', 'ROLE_STORE_MANAGER')")
    ProductDto updateProduct(Long id, UpdateProductRequest request, HttpServletRequest request2);

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_SUPER_ADMIN', 'ROLE_STORE_MANAGER')")
    void deleteProduct(Long id, HttpServletRequest request2);

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_SUPER_ADMIN', 'ROLE_STORE_MANAGER')")
    List<ProductDto> getAllProductsByStoreId(Long storeI, HttpServletRequest request2);

    List<ProductDto> searchProducts(Long storeId, String query);
}
