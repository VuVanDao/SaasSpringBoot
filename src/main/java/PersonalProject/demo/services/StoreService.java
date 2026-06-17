package PersonalProject.demo.services;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;

import PersonalProject.demo.Dto.Request.CreateStoreRequest;
import PersonalProject.demo.Dto.Request.UpdateStoreRequest;
import PersonalProject.demo.Dto.Response.StoreDto;
import PersonalProject.demo.Enums.StoreStatus;

public interface StoreService {
    @PreAuthorize("hasRole('ROLE_STORE_MANAGER')")
    StoreDto createStore(CreateStoreRequest storeDto, Long tenantId);

    StoreDto getStoreById(Long id, Long tenantId);

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_SUPER_ADMIN')")
    List<StoreDto> getAllStores(Long tenantId);

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_SUPER_ADMIN','ROLE_STORE_MANAGER')")
    StoreDto getStoreByStoreManager(Long tenantId);

    @PreAuthorize("hasRole('ROLE_STORE_MANAGER')")
    StoreDto updateStore(Long id, UpdateStoreRequest storeDto, Long tenantId);

    @PreAuthorize("hasRole('ROLE_STORE_MANAGER')")
    void deleteStore(Long id, Long tenantId);

    StoreDto getStoreByEmployee(Long tenantId);
    
    @PreAuthorize("hasRole('ROLE_STORE_MANAGER')")
    StoreDto moderateStore(Long id, StoreStatus storeStatus, Long tenantId);
}
