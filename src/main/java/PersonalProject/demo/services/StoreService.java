package PersonalProject.demo.services;


import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;

import PersonalProject.demo.Dto.Request.CreateStoreRequest;
import PersonalProject.demo.Dto.Request.CreateUserRequest;
import PersonalProject.demo.Dto.Request.UpdateStoreRequest;
import PersonalProject.demo.Dto.Response.StoreDto;
import PersonalProject.demo.domain.StoreStatus;
import jakarta.servlet.http.HttpServletRequest;

public interface StoreService {
    @PreAuthorize("hasRole('ROLE_STORE_MANAGER')")
    StoreDto createStore(CreateStoreRequest storeDto, HttpServletRequest request);

    StoreDto getStoreById(Long id);

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_SUPER_ADMIN')")
    List<StoreDto> getAllStores(HttpServletRequest request);

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_SUPER_ADMIN','ROLE_STORE_MANAGER')")
    StoreDto getStoreByStoreManager(HttpServletRequest request);

    @PreAuthorize("hasRole('ROLE_STORE_MANAGER')")
    StoreDto updateStore(Long id, UpdateStoreRequest storeDto, HttpServletRequest request);

    @PreAuthorize("hasRole('ROLE_STORE_MANAGER')")
    void deleteStore(Long id,HttpServletRequest request);

    StoreDto getStoreByEmployee(HttpServletRequest request);
    
    @PreAuthorize("hasRole('ROLE_STORE_MANAGER')")
    StoreDto moderateStore(Long id, StoreStatus storeStatus, HttpServletRequest request);
}
