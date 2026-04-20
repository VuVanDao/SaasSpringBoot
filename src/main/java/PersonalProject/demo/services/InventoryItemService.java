package PersonalProject.demo.services;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.security.access.prepost.PreAuthorize;

import PersonalProject.demo.Dto.Request.CreateInventoryItemRequest;
import PersonalProject.demo.Dto.Response.InventoryItemInventory;
import PersonalProject.demo.Dto.Response.InventoryItemProduct;
import PersonalProject.demo.Dto.Response.ProductDto;
import jakarta.servlet.http.HttpServletRequest;

public interface InventoryItemService {
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_SUPER_ADMIN', 'ROLE_STORE_MANAGER')")
    // thêm nhiều sản phẩm cùng só lượng của chúng vào 1 inventory
    void AddProductsToInventory(CreateInventoryItemRequest createRequest, Long inventoryId, HttpServletRequest request);

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_SUPER_ADMIN', 'ROLE_STORE_MANAGER')")
    // thêm 1 sản phẩm vào nhiều inventory
    void AddProductToInventories(CreateInventoryItemRequest createRequest, Long productId, HttpServletRequest request);
    
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_SUPER_ADMIN', 'ROLE_STORE_MANAGER')")
    List<InventoryItemProduct> getProductsInInventory(Long inventoryId, HttpServletRequest request);

    List<InventoryItemInventory> getInventoriesByProductId(Long productId, HttpServletRequest request);
}
