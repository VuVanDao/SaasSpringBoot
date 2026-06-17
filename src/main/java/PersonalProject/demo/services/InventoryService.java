package PersonalProject.demo.services;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;

import PersonalProject.demo.Dto.Request.CreateInventoryRequest;
import PersonalProject.demo.Dto.Request.UpdateInventoryRequest;
import PersonalProject.demo.Dto.Response.InventoryDto;

public interface InventoryService {
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_SUPER_ADMIN', 'ROLE_STORE_MANAGER')")
    InventoryDto createInventory(CreateInventoryRequest request, Long tenantId);

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_SUPER_ADMIN', 'ROLE_STORE_MANAGER')")
    InventoryDto updateInventory(Long id, UpdateInventoryRequest request, Long tenantId);

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_SUPER_ADMIN', 'ROLE_STORE_MANAGER')")
    void deleteInventory(Long id, Long tenantId);

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_SUPER_ADMIN', 'ROLE_STORE_MANAGER')")
    InventoryDto getInventoryById(Long id, Long tenantId);

    InventoryDto getProductInInventory(Long productId);

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_SUPER_ADMIN', 'ROLE_STORE_MANAGER')")
    List<InventoryDto> getAllInventoryByBranchId(Long branchId, Long tenantId);

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_SUPER_ADMIN', 'ROLE_STORE_MANAGER')")
    List<InventoryDto> getAllInventory(Long tenantId);
}
