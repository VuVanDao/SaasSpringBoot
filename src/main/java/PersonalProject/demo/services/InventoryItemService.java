package PersonalProject.demo.services;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;

import PersonalProject.demo.Dto.Request.CreateInventoryItemRequest;
import PersonalProject.demo.Dto.Response.InventoryItemInventory;
import PersonalProject.demo.Dto.Response.InventoryItemProduct;

public interface InventoryItemService {
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_SUPER_ADMIN', 'ROLE_STORE_MANAGER')")
    void AddProductsToInventory(CreateInventoryItemRequest createRequest, Long inventoryId, Long tenantId);

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_SUPER_ADMIN', 'ROLE_STORE_MANAGER')")
    void AddProductToInventories(CreateInventoryItemRequest createRequest, Long productId, Long tenantId);
    
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_SUPER_ADMIN', 'ROLE_STORE_MANAGER')")
    List<InventoryItemProduct> getProductsInInventory(Long inventoryId, Long tenantId);

    List<InventoryItemInventory> getInventoriesByProductId(Long productId, Long tenantId);
}
