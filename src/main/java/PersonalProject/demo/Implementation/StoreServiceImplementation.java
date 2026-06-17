package PersonalProject.demo.Implementation;

import java.util.HashSet;
import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import PersonalProject.demo.Dto.Request.CreateStoreRequest;
import PersonalProject.demo.Dto.Request.UpdateStoreRequest;
import PersonalProject.demo.Dto.Response.StoreDto;
import PersonalProject.demo.Dto.Response.UserDto;
import PersonalProject.demo.Enums.ErrorCode;
import PersonalProject.demo.Enums.StoreStatus;
import PersonalProject.demo.Enums.UserRole;
import PersonalProject.demo.exception.ResourceNotFoundException;
import PersonalProject.demo.mapper.storeMapper;
import PersonalProject.demo.models.Branch;
import PersonalProject.demo.models.Category;
import PersonalProject.demo.models.Store;
import PersonalProject.demo.models.User;
import PersonalProject.demo.repositories.BranchRepository;
import PersonalProject.demo.repositories.CategoryRepositories;
import PersonalProject.demo.repositories.StoreRepositories;
import PersonalProject.demo.repositories.UserRepository;
import PersonalProject.demo.services.StoreService;
import PersonalProject.demo.services.UserService;
import PersonalProject.demo.utils.TenantUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class StoreServiceImplementation implements StoreService {
    private final StoreRepositories storeRepositories;
    private final UserRepository userRepository;
    private final UserService userService;
    private final storeMapper storeMapper;
    private final BranchRepository branchRepository;
    private final CategoryRepositories categoryRepositories;
    private final TenantUtil tenantUtil;

    @Override
    public StoreDto createStore(CreateStoreRequest storeDto, Long tenantId) {
        tenantUtil.validateTenant(tenantId);
        if (tenantId == null) {
            throw new RuntimeException("Missing tenant");
        }
        if (!tenantId.equals(storeDto.getTenantId())) {
            throw new RuntimeException("your tenant is not match with store request");
        }
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email);
        if (user.getRole() != UserRole.ROLE_STORE_MANAGER) {
            throw new RuntimeException("you have not permission to create store");
        }
        Store store = storeMapper.convertToModel(storeDto, user);
        Store savedStore = storeRepositories.save(store);
        return storeMapper.convertToDto(savedStore);
    }

    @Override
    public StoreDto getStoreById(Long id, Long tenantId) {
        tenantUtil.validateTenant(tenantId);
        Store store = storeRepositories.findByIdAndTenantId(id, tenantId);
        if (store == null) {
            throw new ResourceNotFoundException(ErrorCode.Resource_not_found);
        }
        return storeMapper.convertToDto(store);
    }

    @Override
    @Transactional
    public List<StoreDto> getAllStores(Long tenantId) {
        tenantUtil.validateTenant(tenantId);
        List<Store> stores = storeRepositories.findAllByTenantId(tenantId);
        return stores.stream().map(storeMapper::convertToDto).toList();
    }

    @Override
    @Transactional
    public StoreDto updateStore(Long id, UpdateStoreRequest storeDto, Long tenantId) {
        tenantUtil.validateTenant(tenantId);
        if (tenantId == null) {
            throw new RuntimeException("Missing tenant");
        }
        Store existingStore = storeRepositories.findByIdAndTenantId(id, tenantId);
        if (existingStore == null) {
            throw new ResourceNotFoundException(ErrorCode.Resource_not_found);
        }

        existingStore.setBrand(storeDto.getBrand());
        existingStore.setDescription(storeDto.getDescription());

        if (storeDto.getStoreContact() != null) {
            existingStore.setStoreContact(storeDto.getStoreContact());
        }

        if (storeDto.getBranchIds() != null && !storeDto.getBranchIds().isEmpty()) {
            List<Branch> branches = branchRepository.findAllByIdInAndTenantId(storeDto.getBranchIds(), tenantId);
            if (branches.size() != storeDto.getBranchIds().size()) {
                throw new IllegalArgumentException("Some branch ids are invalid or not under this tenant");
            }
            existingStore.setBranches(branches);
        }

        if (storeDto.getCategoryIds() != null && !storeDto.getCategoryIds().isEmpty()) {
            List<Category> categories = categoryRepositories.findAllByCategoryIdsAndTenantId(new HashSet<>(storeDto.getCategoryIds()),
                    tenantId);
            if (categories.size() != storeDto.getCategoryIds().size()) {
                throw new IllegalArgumentException("Some category ids are invalid or not under this tenant");
            }
            existingStore.setCategories(new HashSet<>(categories));
        }
        Store updatedStore = storeRepositories.save(existingStore);
        return storeMapper.convertToDto(updatedStore);
    }

    @Override
    public void deleteStore(Long id, Long tenantId) {
        tenantUtil.validateTenant(tenantId);
        if (tenantId == null) {
            throw new RuntimeException("Missing tenant");
        }
        Store existingStore = storeRepositories.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException((ErrorCode.Resource_not_found)));
        if (!tenantId.equals(existingStore.getTenantId())) {
            throw new RuntimeException("you dont have permission to delete this store");
        }
        storeRepositories.delete(existingStore);
    }

    @Override
    @Transactional
    public StoreDto getStoreByEmployee(Long tenantId) {
        tenantUtil.validateTenant(tenantId);
        if (tenantId == null) {
            throw new RuntimeException("Missing tenant");
        }
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email); 
        if (user.getStore() == null) {
            throw new ResourceNotFoundException((ErrorCode.Resource_not_found));
        }
        
        return StoreDto.builder()
                .id(user.getStore().getId())
                .brand(user.getStore().getBrand())
                .description(user.getStore().getDescription())
                .storeAdmin(storeMapper.convertToDto(user.getStore()).getStoreAdmin())
                .storeContact(user.getStore().getStoreContact())
                .storeStatus(user.getStore().getStoreStatus())
                .build();
    }

    @Override
    public StoreDto moderateStore(Long id, StoreStatus storeStatus, Long tenantId) {
        tenantUtil.validateTenant(tenantId);
        Store existingStore = storeRepositories.findByIdAndTenantId(id, tenantId);
        if (existingStore == null) {
            throw new ResourceNotFoundException(ErrorCode.Resource_not_found);
        }
        existingStore.setStoreStatus(storeStatus);
        Store updatedStore = storeRepositories.save(existingStore);
        return storeMapper.convertToDto(updatedStore);
    }

    @Override
    @Transactional
    public StoreDto getStoreByStoreManager(Long tenantId) {
        UserDto currentUser = tenantUtil.validateTenantAndGetUser(tenantId);
        System.out.println("User ID: " + currentUser.getId());
        Store store = storeRepositories.findIncludeCategory(currentUser.getId());
        if (store == null) {
            throw new ResourceNotFoundException((ErrorCode.Resource_not_found));
        }
        return storeMapper.convertToDto(store);
    }
}
