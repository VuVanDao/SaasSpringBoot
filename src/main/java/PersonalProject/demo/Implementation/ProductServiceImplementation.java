package PersonalProject.demo.Implementation;

import java.security.Security;
import java.util.List;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import PersonalProject.demo.Dto.Request.CreateProductRequest;
import PersonalProject.demo.Dto.Request.UpdateProductRequest;
import PersonalProject.demo.Dto.Response.ProductDto;
import PersonalProject.demo.Dto.Response.StoreDto;
import PersonalProject.demo.Dto.Response.UserDto;
import PersonalProject.demo.exception.ResourceNotFoundException;
import PersonalProject.demo.mapper.ProductMapper;
import PersonalProject.demo.models.Products;
import PersonalProject.demo.models.Store;
import PersonalProject.demo.repositories.ProductRepository;
import PersonalProject.demo.repositories.StoreRepositories;
import PersonalProject.demo.services.ProductService;
import PersonalProject.demo.services.StoreService;
import PersonalProject.demo.services.UserService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImplementation implements ProductService {
    
    private final ProductRepository productRepository;
    private final StoreRepositories storeRepositories;
    private final ProductMapper productMapper;
    private final StoreService storeService;
    private final UserService userService;

    @Override
    public ProductDto createProduct(CreateProductRequest request) {
        UserDto currentUser = userService.getCurrentUser();
        Store store = storeRepositories.findById(currentUser.getStore().getId()).orElseThrow(() -> new ResourceNotFoundException("Store not found with id: " + currentUser.getStore().getId()));
        Products products = productMapper.convertToModel(request, store); // store sẽ được set sau khi lấy từ DB
        Products savedProduct = productRepository.save(products);
        return productMapper.convertToDto(savedProduct,true);
    }
    @Override
    public List<ProductDto> getAllProducts() {
        return productRepository.findAll().stream().map(product -> productMapper.convertToDto(product,false)).toList();
    }
    @Override
    public ProductDto getProductById(Long id) {
        Products product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        return productMapper.convertToDto(product,true);
    }
    @Override
    public ProductDto updateProduct(Long id, UpdateProductRequest request) {
        Products existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        if (existingProduct.getStore().getId() != userService.getCurrentUser().getStore().getId()) {
            throw new ResourceNotFoundException("Product with id: " + id + " not found in your store");
        }
        existingProduct = productMapper.convertToModel(request, existingProduct);
        Products updatedProduct = productRepository.save(existingProduct);
        return productMapper.convertToDto(updatedProduct,true);
    }
    @Override
    public void deleteProduct(Long id) {
        Products existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        if (existingProduct.getStore().getId() != userService.getCurrentUser().getStore().getId()) {
            throw new ResourceNotFoundException("Product with id: " + id + " not found in your store");
        }
        productRepository.delete(existingProduct);
    }
    @Override
    public List<ProductDto> getProductsByStoreId(Long storeId) {
        // if(userService.getCurrentUser().getStore().getId() != storeId) {
        //     throw new ResourceNotFoundException("Store with id: " + storeId + " is not your store");
        // }
        List<Products> products = productRepository.findByStoreId(storeId);
        return products.stream().map(product -> productMapper.convertToDto(product,false)).toList();
    }
    @Override
    public List<ProductDto> searchProducts(Long storeId, String query) {
        List<Products> products = productRepository.searchByQuery(storeId, query);
        return products.stream().map(product -> productMapper.convertToDto(product,false)).toList();
    }


}
