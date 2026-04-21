package PersonalProject.demo.Implementation;

import java.security.Security;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import PersonalProject.demo.Dto.Request.CreateProductRequest;
import PersonalProject.demo.Dto.Request.UpdateProductRequest;
import PersonalProject.demo.Dto.Response.ProductDto;
import PersonalProject.demo.Dto.Response.StoreDto;
import PersonalProject.demo.Dto.Response.UserDto;
import PersonalProject.demo.domain.ErrorCode;
import PersonalProject.demo.exception.ResourceNotFoundException;
import PersonalProject.demo.mapper.ProductMapper;
import PersonalProject.demo.models.Category;
import PersonalProject.demo.models.Products;
import PersonalProject.demo.models.Store;
import PersonalProject.demo.repositories.CategoryRepositories;
import PersonalProject.demo.repositories.ProductRepository;
import PersonalProject.demo.repositories.StoreRepositories;
import PersonalProject.demo.services.ProductService;
import PersonalProject.demo.services.StoreService;
import PersonalProject.demo.services.UserService;
import PersonalProject.demo.utils.TenantUtil;
import jakarta.servlet.http.HttpServletRequest;
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

    @Override
    public ProductDto createProduct(CreateProductRequest request, HttpServletRequest request2) {
        UserDto currentUser = tenantUtil.validateTenantAndGetUser(request2);
        List<Category> categories = categoryRepositories.findByCategoryIdsAndTenantId(request.getCategory_id(),
                currentUser.getTenantId());
        Products products = productMapper.convertToModel(request, categories);
        Products savedProduct = productRepository.save(products);
        return productMapper.convertToDto(savedProduct, true);
    }

    // CHECK: hàm này 1 là dùng cho viẹc lấy all sản phẩm, service của admin
    @Override
    // @Transactional
    public List<ProductDto> getAllProducts(HttpServletRequest request2) {
        // return productRepository.findAll().stream().map(product -> productMapper.convertToDto(product,false)).toList();
        return productRepository.findAllWithCategories().stream().map(product -> productMapper.convertToDto(product,false)).toList();
    }

    @Override
    @Transactional
    public ProductDto getProductById(Long id, HttpServletRequest request2) {
        Long tenantId = tenantUtil.validateTenant(request2);
        Products product = productRepository.findAllByIdAndTenantId(id,tenantId).orElseThrow(() -> new ResourceNotFoundException((ErrorCode.Resource_not_found)));
        return productMapper.convertToDto(product,true);
    }

    @Override
    @Transactional
    public ProductDto updateProduct(Long id, UpdateProductRequest request, HttpServletRequest request2) {
        Long tenantId = tenantUtil.validateTenant(request2);
        Products existingProduct = productRepository.findAllByIdAndTenantId(id,tenantId)
                .orElseThrow(() -> new ResourceNotFoundException((ErrorCode.Resource_not_found)));
        Store store = storeRepositories.findAByIdAndTenantId(request.getStore_id(), tenantId);
        if (store == null) {
            throw new ResourceNotFoundException(ErrorCode.Resource_not_found);
        }
        Set<Long> cateOfStore = store.getCategories().stream().map(Category::getId)
                .collect(Collectors.toSet());

        List<Long> requestUpdateCate = request.getCategoryIds();
        List<Long> categoriesNotBelongToStore = requestUpdateCate.stream()
                .filter(cateId -> !cateOfStore.contains(cateId))
                .collect(Collectors.toList()); 
        if (!categoriesNotBelongToStore.isEmpty()) {
            // hoặc có thể chọn am thầm xoá những cate không hợp lệ
            throw new IllegalArgumentException("This category with id : " + categoriesNotBelongToStore + " is not affiliated with this store");
        }
        existingProduct = productMapper.convertToModel(request, existingProduct);
        Products updatedProduct = productRepository.save(existingProduct);
        return productMapper.convertToDto(updatedProduct,true);
    }
    @Override
    public void deleteProduct(Long id, HttpServletRequest request2) {
        Long tenantId = tenantUtil.validateTenant(request2);
        Products existingProduct = productRepository.findAllByIdAndTenantId(id,tenantId)
                .orElseThrow(() -> new ResourceNotFoundException((ErrorCode.Resource_not_found)));
        productRepository.delete(existingProduct);
    }

    @Override
    @Transactional
    public List<ProductDto> getAllProductsByStoreId(Long storeId, HttpServletRequest request2) {
        Long tenantId = tenantUtil.validateTenant(request2);
        return productRepository.findAllWithStoreIdAndTenantId(storeId, tenantId).stream()
                .map(product -> productMapper.convertToDto(product, false)).toList();
    }
    // CHECK: xem lại hàm này
    @Override
    public List<ProductDto> searchProducts(Long storeId, String query) {
        // List<Products> products = productRepository.searchByQuery(storeId, query);
        // return products.stream().map(product -> productMapper.convertToDto(product,false)).toList();
        return null;
    }


}
