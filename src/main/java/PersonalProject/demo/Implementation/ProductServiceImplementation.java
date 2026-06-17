package PersonalProject.demo.Implementation;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import PersonalProject.demo.Dto.Request.CreateProductRequest;
import PersonalProject.demo.Dto.Request.UpdateProductRequest;
import PersonalProject.demo.Dto.Response.ProductDto;
import PersonalProject.demo.Dto.Response.UserDto;
import PersonalProject.demo.Enums.ErrorCode;
import PersonalProject.demo.exception.ResourceNotFoundException;
import PersonalProject.demo.mapper.ProductMapper;
import PersonalProject.demo.models.Category;
import PersonalProject.demo.models.Products;
import PersonalProject.demo.models.Store;
import PersonalProject.demo.models.StoreProduct;
import PersonalProject.demo.repositories.CategoryRepositories;
import PersonalProject.demo.repositories.ProductRepository;
import PersonalProject.demo.repositories.StoreRepositories;
import PersonalProject.demo.repositories.StoreProductRepository;
import PersonalProject.demo.services.ProductService;
import PersonalProject.demo.services.StoreService;
import PersonalProject.demo.services.UserService;
import PersonalProject.demo.utils.TenantUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImplementation implements ProductService {
    
    private final ProductRepository productRepository;
    private final StoreRepositories storeRepositories;
    private final ProductMapper productMapper;
    private final StoreService storeService;
    private final UserService userService;
    private final TenantUtil tenantUtil;
    private final CategoryRepositories categoryRepositories;
    private final StoreProductRepository storeProductRepository;

    @Override
    @Transactional
    public ProductDto createProduct(CreateProductRequest request, Long tenantId) {
        UserDto currentUser = tenantUtil.validateTenantAndGetUser(tenantId);
        List<Category> categories = categoryRepositories.findAllByCategoryIdsAndTenantId(request.getCategory_id(),
                currentUser.getTenantId());
        Products products = productMapper.convertToModel(request, categories);
        Products savedProduct = productRepository.save(products);

        if (request.getStoreId() != null) {
            Store store = storeRepositories.findByIdAndTenantId(request.getStoreId(), tenantId);
            if (store != null) {
                StoreProduct storeProduct = StoreProduct.builder()
                        .tenantId(tenantId)
                        .store(store)
                        .product(savedProduct)
                        .build();
                storeProductRepository.save(storeProduct);
            }
        }

        return productMapper.convertToDto(savedProduct, true);
    }

    @Override
    public List<ProductDto> getAllProducts(Long tenantId) {
        tenantUtil.validateTenant(tenantId);
        return productRepository.findAllWithCategoriesAndTenantId(tenantId).stream().map(product -> productMapper.convertToDto(product, false)).toList();
    }

    @Override
    @Transactional
    public ProductDto getProductById(Long id, Long tenantId) {
        tenantUtil.validateTenant(tenantId);
        Products product = productRepository.findAllByIdAndTenantId(id, tenantId).orElseThrow(() -> new ResourceNotFoundException((ErrorCode.Resource_not_found)));
        return productMapper.convertToDto(product, true);
    }

    @Override
    @Transactional
    public ProductDto updateProduct(Long id, UpdateProductRequest request, Long tenantId) {
        tenantUtil.validateTenant(tenantId);
        Products existingProduct = productRepository.findAllByIdAndTenantId(id, tenantId)
                .orElseThrow(() -> new ResourceNotFoundException((ErrorCode.Resource_not_found)));
        Store store = storeRepositories.findByIdAndTenantId(request.getStore_id(), tenantId);
        if (store == null) {
            throw new ResourceNotFoundException(ErrorCode.Resource_not_found);
        }
        
        // Ensure the product is associated with the store
        if (!storeProductRepository.existsByStoreIdAndProductId(store.getId(), existingProduct.getId())) {
            StoreProduct storeProduct = StoreProduct.builder()
                    .tenantId(tenantId)
                    .store(store)
                    .product(existingProduct)
                    .build();
            storeProductRepository.save(storeProduct);
        }

        Set<Long> cateOfStore = store.getCategories().stream().map(Category::getId)
                .collect(Collectors.toSet());

        List<Long> requestUpdateCate = request.getCategoryIds();
        List<Long> categoriesNotBelongToStore = requestUpdateCate.stream()
                .filter(cateId -> !cateOfStore.contains(cateId))
                .collect(Collectors.toList()); 
        if (!categoriesNotBelongToStore.isEmpty()) {
            throw new IllegalArgumentException("This category with id : " + categoriesNotBelongToStore + " is not affiliated with this store");
        }
        existingProduct = productMapper.convertToModel(request, existingProduct);
        Products updatedProduct = productRepository.save(existingProduct);
        return productMapper.convertToDto(updatedProduct, true);
    }

    @Override
    public void deleteProduct(Long id, Long tenantId) {
        tenantUtil.validateTenant(tenantId);
        Products existingProduct = productRepository.findAllByIdAndTenantId(id, tenantId)
                .orElseThrow(() -> new ResourceNotFoundException((ErrorCode.Resource_not_found)));
        productRepository.delete(existingProduct);
    }

    @Override
    @Transactional
    public List<ProductDto> getAllProductsByStoreId(Long storeId, Long tenantId) {
        tenantUtil.validateTenant(tenantId);
        return productRepository.findAllWithStoreIdAndTenantId(storeId, tenantId).stream()
                .map(product -> productMapper.convertToDto(product, false)).toList();
    }

    @Override
    public List<ProductDto> searchProducts(Long storeId, String query) {
        return null;
    }
}
