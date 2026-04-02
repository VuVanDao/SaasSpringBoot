package PersonalProject.demo.services;

import PersonalProject.demo.Dto.Request.CreateProductRequest;
import PersonalProject.demo.Dto.Request.UpdateProductRequest;
import PersonalProject.demo.Dto.Response.ProductDto;

import java.util.List;

public interface ProductService {

    ProductDto createProduct(CreateProductRequest request);

    List<ProductDto> getAllProducts();

    ProductDto getProductById(Long id);

    ProductDto updateProduct(Long id, UpdateProductRequest request);

    void deleteProduct(Long id);

    List<ProductDto> getProductsByStoreId(Long storeId);

    List<ProductDto> searchProducts(Long storeId, String query);
}
