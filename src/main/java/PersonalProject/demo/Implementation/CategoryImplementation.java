package PersonalProject.demo.Implementation;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.stereotype.Service;

import PersonalProject.demo.Dto.Request.CreateCategoryRequest;
import PersonalProject.demo.Dto.Response.CategoryResponse;
import PersonalProject.demo.Dto.Response.UserDto;
import PersonalProject.demo.Enums.ErrorCode;
import PersonalProject.demo.Enums.UserRole;
import PersonalProject.demo.exception.ResourceNotFoundException;
import PersonalProject.demo.models.Category;
import PersonalProject.demo.models.Store;
import PersonalProject.demo.repositories.CategoryRepositories;
import PersonalProject.demo.repositories.StoreRepositories;
import PersonalProject.demo.services.CategoryService;
import PersonalProject.demo.services.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryImplementation implements CategoryService {
    private final CategoryRepositories categoryRepositories;
    private final StoreRepositories storeRepositories;
    private final UserService userService;

    @Override
    @Transactional
    public CategoryResponse createCategory(CreateCategoryRequest request, Long tenantId) {
        System.out.println("-------------------CategoryImplementation.createCategory-------------------------");
        if (tenantId == null) {
            throw new RuntimeException("Missing tenant");
        }
        UserDto user = userService.getCurrentUser();

        if (!user.getTenantId().equals(tenantId)) {
            throw new BadCredentialsException(ErrorCode.BadCredentialsException.getMessage());
        }
        // chia luồng: admin tạo cate public và store manager tạo 
        // 1. do admin tạo , tenant = 1 thuwfong laf do admin
        if (tenantId == 1) {
            System.out.println("-------------------luồng 1-------------------");
            Category newCategory = Category.builder().name(request.getName())
                    .tenantId(tenantId)
                    .isSystemDefault(request.getIsSystemDefault()).build();
            categoryRepositories.save(newCategory);
            return CategoryResponse.builder().name(newCategory.getName()).id(newCategory.getId()).isSystemDefault(newCategory.getIsSystemDefault()).build();
        } else {
            System.out.println("-------------------luồng 2-------------------");
            // 2. do store admin tạo, lúc này cần check kĩ hơn
            // do của store admin tạo nên nó chỉ thuộc về store đó
            if (request.getStoreId().size() > 1) {
                log.info("Error in request.getStoreId().size() > 1");
                throw new RuntimeException("Error when create category");
            }
            // tìm store
            List<Store> stores = storeRepositories.findAllByIdAndTenantId(request.getStoreId(), tenantId);
            // truwong hop ko tim thay store
            if (stores == null || stores.isEmpty()) {
                log.info("Error in stores == null || stores.isEmpty()");
                throw new ResourceNotFoundException(ErrorCode.Resource_not_found);
            }
            // truong hop store tenantID ko trung tenantID
            Long StoreTenantId = stores.get(0).getTenantId();
            if (!StoreTenantId.equals(tenantId) || !user.getTenantId().equals(StoreTenantId)) {
                throw new AuthorizationDeniedException(ErrorCode.AuthorizationDeniedException.getMessage());
            }
            Category newCategory = Category.builder().name(request.getName()).tenantId(tenantId)
                    .isSystemDefault(false).build();
            categoryRepositories.save(newCategory);

            // Thiết lập mối quan hệ với stores
            newCategory.setStores(stores);
            
            // Cập nhật mối quan hệ từ phía Store
            for (Store store : stores) {
                if (store.getCategories() == null) {
                    store.setCategories(new HashSet<>());
                }
                store.getCategories().add(newCategory);
                
                // Cập nhật lại store
                storeRepositories.save(store);
            }
            return CategoryResponse.builder()
                .name(newCategory.getName())
                .id(newCategory.getId())
                .isSystemDefault(newCategory.getIsSystemDefault())
                .build();
        }
    }

    @Override
    public List<CategoryResponse> getAllCategories(Long tenantId) {
        if (tenantId == null) {
            throw new RuntimeException("Missing tenant");
        }
        UserDto user = userService.getCurrentUser();

        if (!user.getTenantId().equals(tenantId)) {
            throw new BadCredentialsException(ErrorCode.BadCredentialsException.getMessage());
        }
        System.out.println("------------------USER LOGIN________"+user);
        if (user.getRole() == UserRole.ROLE_STORE_MANAGER) {
            // Nếu là store admin, lấy cả categories công khai và categories của store riêng
            List<Store> stores = storeRepositories.findAllByTenantId(tenantId);
            if (stores.isEmpty()) {
                return Collections.emptyList();
            }
            Long storeId = stores.get(0).getId();
            List<Category> categories = categoryRepositories.findCategoriesByStoreId(storeId);
            return categories.stream()
                .map(category -> CategoryResponse.builder()
                        .id(category.getId())
                        .name(category.getName())
                        .isSystemDefault(category.getIsSystemDefault())
                        .build())
                .toList();
        }
        // Nếu là admin, lấy tất cả categories
        return categoryRepositories.findAll().stream()
            .map(category -> CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .isSystemDefault(category.getIsSystemDefault())
                .build())
            .toList();
    }

    @Override
    @Transactional
    public CategoryResponse getCategoryById(Long id, Long tenantId) {
        if (tenantId == null) {
            throw new RuntimeException("Missing tenant");
        }
        UserDto user = userService.getCurrentUser();

        if (!user.getTenantId().equals(tenantId)) {
            throw new BadCredentialsException(ErrorCode.BadCredentialsException.getMessage());
        }
        Category existingCategory = categoryRepositories.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));

         // Phân quyền truy cập
         if (user.getRole() == UserRole.ROLE_STORE_MANAGER) {
             // Store manager chỉ được truy cập category công khai hoặc category của chính store
             if (!existingCategory.getIsSystemDefault() &&
                     !existingCategory.getTenantId().equals(user.getTenantId())) {
                 throw new BadCredentialsException(ErrorCode.BadCredentialsException.getMessage());
             }
         }
        // Nếu là ADMIN hoặc SUPER_ADMIN thì có thể truy cập mọi category
        return CategoryResponse.builder().id(existingCategory.getId()).name(existingCategory.getName()).isSystemDefault(existingCategory.getIsSystemDefault()).build();
    }

    @Override
    @Transactional
    public void deleteCategoryById(Long id, Long tenantId) {
        if (tenantId == null) {
            throw new RuntimeException("Missing tenant");
        }
        UserDto user = userService.getCurrentUser();

        if (!user.getTenantId().equals(tenantId)) {
            throw new BadCredentialsException(ErrorCode.BadCredentialsException.getMessage());
        }
        Category existingCategory = categoryRepositories.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));
        // 2 th: cate public và cate private
        // 1. cate public (chi co admin mơi duoc xoa)
        if (user.getRole() == UserRole.ROLE_SUPER_ADMIN) {
            deleteCategoryAndCleanRelationship(existingCategory);
            return;
        }

        // 2. cate private
        // Phân quyền truy cập
        if (user.getRole() == UserRole.ROLE_STORE_MANAGER) {
            // Store manager chỉ xoá category của chính store
            // Kiểm tra nếu là category công khai (isSystemDefault = true) thì không cho xóa
            if (existingCategory.getIsSystemDefault()) {
                throw new BadCredentialsException("Không thể xóa category công khai!");
            }
            // Kiểm tra xem category này thuộc store của user không
            if (!existingCategory.getTenantId().equals(user.getTenantId())) {
                throw new BadCredentialsException(ErrorCode.BadCredentialsException.getMessage());
            }
            // Xóa category và loại bỏ khỏi relationship với store
            deleteCategoryAndCleanRelationship(existingCategory);
            return;
        }
        throw new BadCredentialsException("Không có quyền xóa category!");
    }

    // Helper method để xóa category và cleanup relationship
    private void deleteCategoryAndCleanRelationship(Category categoryToDelete) {
        // Trước tiên, loại bỏ category khỏi các store liên quan
        if (categoryToDelete.getStores() != null && !categoryToDelete.getStores().isEmpty()) {
            for (Store store : categoryToDelete.getStores()) {
                if (store.getCategories() != null) {
                    store.getCategories().remove(categoryToDelete);
                    storeRepositories.save(store); // Lưu lại store sau khi loại bỏ category
                }
            }
        }
        
        // Sau đó xóa category
        categoryRepositories.delete(categoryToDelete);
    }
}
