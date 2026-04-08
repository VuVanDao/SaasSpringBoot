package PersonalProject.demo.Implementation;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import PersonalProject.demo.Dto.Request.CreateStoreRequest;
import PersonalProject.demo.Dto.Request.CreateUserRequest;
import PersonalProject.demo.Dto.Request.UpdateStoreRequest;
import PersonalProject.demo.Dto.Response.StoreDto;
import PersonalProject.demo.Dto.Response.UserDto;
import PersonalProject.demo.domain.StoreStatus;
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
    @Override
    public StoreDto createStore(CreateStoreRequest storeDto) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email); 
        Store store = storeMapper.convertToModel(storeDto, user);
        Store savedStore = storeRepositories.save(store);
        return storeMapper.convertToDto(savedStore);
    }

    @Override
    public StoreDto getStoreById(Long id) {
        Store store = storeRepositories.findById(id).orElseThrow(() -> new ResourceNotFoundException("Store not found"));
        return storeMapper.convertToDto(store);
    }

    @Override
    public List<StoreDto> getAllStores() {
        List<Store> stores = storeRepositories.findAll();
        return stores.stream().map(storeMapper::convertToDto).toList();
    }

    @Override
    @Transactional
    public StoreDto getStoreByAdmin() {
        UserDto currentUser = userService.getCurrentUser();
        System.out.println("User ID: " + currentUser.getId());
        // Store store = storeRepositories.findByStoreAdminId(currentUser.getId());
        Store store = storeRepositories.findIncludeCategory(currentUser.getId());
        if (store == null) {
            throw new ResourceNotFoundException("Store not found for the current admin");
        }
        return storeMapper.convertToDto(store);
    }

    @Override
    public StoreDto updateStore(Long id, UpdateStoreRequest storeDto) {
        Store existingStore = storeRepositories.findById(id).orElseThrow(() -> new ResourceNotFoundException("Store not found"));
        existingStore.setBrand(storeDto.getBrand());
        existingStore.setDescription(storeDto.getDescription());
        if (storeDto.getStoreType() != null) {
            existingStore.setStoreType(storeDto.getStoreType());
        }
        if (storeDto.getStoreContact() != null) {
            existingStore.setStoreContact(storeDto.getStoreContact());
        }
        existingStore.setStoreStatus(storeDto.getStoreStatus());
        List<Branch> branches = branchRepository.findAllById(storeDto.getBranchIds());
        existingStore.setBranches(branches);
        if(storeDto.getCategoryIds().size() > 0) {
            List<Category> categories = categoryRepositories.findAllById(storeDto.getCategoryIds());
            existingStore.setCategories(new HashSet<>(categories));
        }
        Store updatedStore = storeRepositories.save(existingStore);
        return storeMapper.convertToDto(updatedStore);
    }

    @Override
    public void deleteStore(Long id) {
        Store existingStore = storeRepositories.findById(id).orElseThrow(() -> new ResourceNotFoundException("Store not found"));
        storeRepositories.delete(existingStore);
    }

    @Override
    public StoreDto getStoreByEmployee() {
        // UserDto currentUser = userService.getCurrentUser();
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email); 
        if(user.getStore() == null) {
            throw new ResourceNotFoundException("Store not found for the current employee");
        }
        return StoreDto.builder()
                .id(user.getStore().getId())
                .brand(user.getStore().getBrand())
                .description(user.getStore().getDescription())
                .storeAdmin(storeMapper.convertToDto(user.getStore()).getStoreAdmin())
                // .storeAdmin(storeMapper.convertToDto(user.getStore()).getStoreAdmin())
                .storeContact(user.getStore().getStoreContact())
                .storeType(user.getStore().getStoreType())
                .storeStatus(user.getStore().getStoreStatus())
                .build();

    }

    @Override
    public StoreDto moderateStore(Long id, StoreStatus storeStatus) {
        Store existingStore = storeRepositories.findById(id).orElseThrow(() -> new ResourceNotFoundException("Store not found"));
        existingStore.setStoreStatus(storeStatus);
        Store updatedStore = storeRepositories.save(existingStore);
        return storeMapper.convertToDto(updatedStore);
    }
    
}
