package PersonalProject.demo.services;

import java.util.List;

import PersonalProject.demo.Dto.Request.CreateInventoryRequest;
import PersonalProject.demo.Dto.Request.UpdateInventoryRequest;
import PersonalProject.demo.Dto.Response.InventoryDto;

public interface InventoryService {
    InventoryDto createInventory(CreateInventoryRequest request);
    InventoryDto updateInventory(Long id, UpdateInventoryRequest request);
    void deleteInventory(Long id);
    InventoryDto getInventoryById(Long id);
    InventoryDto getProductInInventory(Long productId);
    List<InventoryDto> getAllInventoryByBranchId(Long branchId);
    List<InventoryDto> getAllInventory();
}
