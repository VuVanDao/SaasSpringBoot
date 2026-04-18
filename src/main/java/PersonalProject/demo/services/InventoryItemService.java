package PersonalProject.demo.services;

import java.util.Map;
import java.util.Set;

import PersonalProject.demo.Dto.Request.CreateInventoryItemRequest;
import jakarta.servlet.http.HttpServletRequest;

public interface InventoryItemService {
    // thêm nhiều sản phẩm cùng só lượng của chúng vào 1 inventory
    void AddProductsToInventory(CreateInventoryItemRequest createRequest, Long inventoryId, HttpServletRequest request);

    // thêm 1 sản phẩm vào nhiều inventory
    void AddProductToInventories(CreateInventoryItemRequest createRequest,Long productId, HttpServletRequest request);
}
