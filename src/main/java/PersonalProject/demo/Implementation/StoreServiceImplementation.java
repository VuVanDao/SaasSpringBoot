package PersonalProject.demo.Implementation;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import javax.management.RuntimeErrorException;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import PersonalProject.demo.Dto.Request.CreateStoreRequest;
import PersonalProject.demo.Dto.Request.CreateUserRequest;
import PersonalProject.demo.Dto.Request.UpdateStoreRequest;
import PersonalProject.demo.Dto.Response.StoreDto;
import PersonalProject.demo.Dto.Response.UserDto;
import PersonalProject.demo.configuration.ApplicationProperties;
import PersonalProject.demo.domain.ErrorCode;
import PersonalProject.demo.domain.StoreStatus;
import PersonalProject.demo.domain.UserRole;
import PersonalProject.demo.exception.ResourceNotFoundException;
import PersonalProject.demo.exception.TenantException;
import PersonalProject.demo.mapper.storeMapper;
import PersonalProject.demo.models.Branch;
import PersonalProject.demo.models.Category;
import PersonalProject.demo.models.Store;
import PersonalProject.demo.models.User;
import PersonalProject.demo.repositories.BranchRepository;
import PersonalProject.demo.repositories.CategoryRepositories;
import PersonalProject.demo.repositories.StoreRepositories;
import PersonalProject.demo.repositories.TenantRepository;
import PersonalProject.demo.repositories.UserRepository;
import PersonalProject.demo.services.StoreService;
import PersonalProject.demo.services.UserService;
import PersonalProject.demo.utils.TenantUtil;
import jakarta.servlet.http.HttpServletRequest;
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
    private final TenantRepository tenantRepository;
    private final ApplicationProperties applicationProperties;
    private final TenantUtil tenantUtil;

    @Override
    public StoreDto createStore(CreateStoreRequest storeDto, HttpServletRequest request) {
        Long tenantId = tenantUtil.validateTenant(request);
        if (tenantId == null) {
            throw new RuntimeException("Missing tenant");
        }
        if (tenantId != storeDto.getTenantId()) {
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

    // CHECK: theem cais check tenantId
    @Override
    public StoreDto getStoreById(Long id) {
        Store store = storeRepositories.findById(id).orElseThrow(() -> new ResourceNotFoundException((ErrorCode.Resource_not_found)));
        return storeMapper.convertToDto(store);
    }

    @Override
    @Transactional
    public List<StoreDto> getAllStores(HttpServletRequest request) {
        Long tenantId = tenantUtil.validateTenant(request);
        if (tenantId == null) {
            throw new RuntimeException("Missing tenant");
        }
        List<Store> stores = storeRepositories.findAllStore(tenantId);
        return stores.stream().map(storeMapper::convertToDto).toList();
    }

    // CHECK: theem cais check tenantId
    @Override
    @Transactional
    public StoreDto getStoreByAdmin() {
        // chỉ có nhưng ai là chủ cửa hàng mới có thể dùng
        UserDto currentUser = userService.getCurrentUser();
        System.out.println("User ID: " + currentUser.getId());
        // Store store = storeRepositories.findByStoreAdminId(currentUser.getId());
        Store store = storeRepositories.findIncludeCategory(currentUser.getId());
        if (store == null) {
            throw new ResourceNotFoundException((ErrorCode.Resource_not_found));
        }
        return storeMapper.convertToDto(store);
    }

    @Override
    @Transactional
    public StoreDto updateStore(Long id, UpdateStoreRequest storeDto, HttpServletRequest request) {
        Long tenantId = tenantUtil.validateTenant(request);
        if (tenantId == null) {
            throw new RuntimeException("Missing tenant");
        }
        Store existingStore = storeRepositories.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException((ErrorCode.Resource_not_found)));
        if (existingStore.getTenantId() != tenantId) {
            throw new RuntimeException("You have not permission to update this store");
        }
        existingStore.setBrand(storeDto.getBrand());
        existingStore.setDescription(storeDto.getDescription());

        if (storeDto.getStoreContact() != null) {
            existingStore.setStoreContact(storeDto.getStoreContact());
        }
        existingStore.setStoreStatus(storeDto.getStoreStatus());
        if (storeDto.getBranchIds() != null && storeDto.getBranchIds().size() > 0) {
            List<Branch> branches = branchRepository.findAllById(storeDto.getBranchIds());
            existingStore.setBranches(branches);
        }

        if(storeDto.getCategoryIds() != null && storeDto.getCategoryIds().size() > 0) {
            List<Category> categories = categoryRepositories.findAllById(storeDto.getCategoryIds());
            existingStore.setCategories(new HashSet<>(categories));
        }
        Store updatedStore = storeRepositories.save(existingStore);
        return storeMapper.convertToDto(updatedStore);
    }

    @Override
    public void deleteStore(Long id, HttpServletRequest request) {
        Long tenantId = tenantUtil.validateTenant(request);
        if (tenantId == null) {
            throw new RuntimeException("Missing tenant");
        }
        Store existingStore = storeRepositories.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException((ErrorCode.Resource_not_found)));
        if (tenantId != existingStore.getTenantId()) {
            throw new RuntimeException("you dont have permission to delete this store");
        }
        storeRepositories.delete(existingStore);
    }

    @Override
    @Transactional
    public StoreDto getStoreByEmployee(HttpServletRequest request) {
        // sủa l;ại cái hàm này sau
        // UserDto currentUser = userService.getCurrentUser();
        Long tenantId = tenantUtil.validateTenant(request);
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
                .storeAdmin(storeMapper.convertToDto(user.getStore()).getStoreAdmin())
                .storeContact(user.getStore().getStoreContact())
                .storeStatus(user.getStore().getStoreStatus())
                .build();

    }

    @Override
    public StoreDto moderateStore(Long id, StoreStatus storeStatus, HttpServletRequest request) {
        Long tenantId = tenantUtil.validateTenant(request);
        if (tenantId == null) {
            throw new TenantException(ErrorCode.Tenant_Exception);
        }
        Store existingStore = storeRepositories.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException((ErrorCode.Resource_not_found)));
        if (existingStore.getTenantId() != tenantId) {
            throw new TenantException(ErrorCode.Tenant_Exception);
        }
        existingStore.setStoreStatus(storeStatus);
        Store updatedStore = storeRepositories.save(existingStore);
        return storeMapper.convertToDto(updatedStore);
    }
    
}
