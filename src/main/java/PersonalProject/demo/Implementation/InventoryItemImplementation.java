package PersonalProject.demo.Implementation;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import PersonalProject.demo.Dto.Request.CreateInventoryItemRequest;
import PersonalProject.demo.domain.ErrorCode;
import PersonalProject.demo.exception.ResourceNotFoundException;
import PersonalProject.demo.models.Inventory;
import PersonalProject.demo.models.InventoryItem;
import PersonalProject.demo.models.Products;
import PersonalProject.demo.repositories.InventoryItemRepository;
import PersonalProject.demo.repositories.InventoryRepository;
import PersonalProject.demo.repositories.ProductRepository;
import PersonalProject.demo.services.InventoryItemService;
import PersonalProject.demo.utils.TenantUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InventoryItemImplementation implements InventoryItemService {
    private final InventoryItemRepository inventoryItemRepository;
    private final InventoryRepository inventoryRepository;
    private final ProductRepository productRepository;
    private final TenantUtil tenantUtil;
    
    @Override
    @Transactional
    public void AddProductsToInventory(CreateInventoryItemRequest createRequest, Long inventoryId,
            HttpServletRequest request) {
        Long tenantId = tenantUtil.validateTenant(request);
        System.out.println("------check find inventory--------");
        System.out.println("-----inventoryId: "+inventoryId);
        System.out.println("-----tenantId: "+tenantId);
          // 1. Kiểm tra inventory tồn tại
        Inventory inventory = inventoryRepository.findByIdAndTenantId(inventoryId, tenantId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.Resource_not_found));
        System.out.println("------check find inventory done--------");
        // 2. Xử lý từng sản phẩm trong map
        for (Map.Entry<Long, Integer> entry : createRequest.getProductIdsAndQuantity().entrySet()) {
            Long productId = entry.getKey();
            Integer quantity = entry.getValue();

            System.out.println("------check find product--------");
            System.out.println("-----ProductID: "+productId);
            // Kiểm tra product tồn tại
            Products product = productRepository.findByIdAndTenantId(productId, tenantId)
                    .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.Resource_not_found));

            // Kiểm tra quantity hợp lệ
            if (quantity == null || quantity < 0) {
                throw new IllegalArgumentException("Quantity must be non-negative for productId: " + productId);
            }

            // Tìm InventoryItem hiện tại (nếu có) để cập nhật quantity
            InventoryItem existingItem = inventoryItemRepository.findByInventoryIdAndProductId(inventoryId, productId);

            if (existingItem != null) {
                // Cập nhật quantity nếu tồn tại
                existingItem.setQuantity(quantity);
                inventoryItemRepository.save(existingItem);
            } else {
                // Tạo mới nếu chưa tồn tại
                InventoryItem newItem = InventoryItem.builder()
                        .inventory(inventory)
                        .product(product)
                        .quantity(quantity)
                        .tenantId(tenantId)
                        .build();
                inventoryItemRepository.save(newItem);
            }
        }
    }

    @Override
    public void AddProductToInventories(CreateInventoryItemRequest createRequest, Long productId,
            HttpServletRequest request) {
        Long tenantId = tenantUtil.validateTenant(request);
         // 1. Kiểm tra product tồn tại
        Products product = productRepository.findByIdAndTenantId(productId, tenantId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.Resource_not_found));
        
        Integer quantity = createRequest.getQuantity();
        // 2. Kiểm tra quantity hợp lệ
        if (quantity == null || quantity < 0) {
            throw new IllegalArgumentException("Quantity must be non-negative for productId: " + productId);
        }

        Set<Long> inventoryIds = createRequest.getInventoryIds();
        // 3. Kiểm tra tất cả inventoryId tồn tại
        Map<Long, Inventory> inventoryMap = inventoryRepository.findAllById(inventoryIds)
                .stream()
                .collect(Collectors.toMap(Inventory::getId, inventory -> inventory));

        if (inventoryMap.size() != inventoryIds.size()) {
            Set<Long> foundIds = inventoryMap.keySet();
            Set<Long> missingIds = inventoryIds.stream()
                    .filter(id -> !foundIds.contains(id))
                    .collect(Collectors.toSet());
            throw new IllegalArgumentException("Inventory not found with ids: " + missingIds);
        }

        // 4. Xử lý từng inventory
        for (Long inventoryId : inventoryIds) {
            Inventory inventory = inventoryMap.get(inventoryId);

            // Tìm InventoryItem hiện tại (nếu có) để cập nhật quantity
            InventoryItem existingItem = inventoryItemRepository.findByInventoryIdAndProductId(inventoryId, productId);

            if (existingItem != null) {
                // Cập nhật quantity nếu tồn tại
                existingItem.setQuantity(quantity);
                inventoryItemRepository.save(existingItem);
            } else {
                // Tạo mới nếu chưa tồn tại
                InventoryItem newItem = InventoryItem.builder()
                        .inventory(inventory)
                        .product(product)
                        .quantity(quantity)
                        .tenantId(tenantId)
                        .build();
                inventoryItemRepository.save(newItem);
            }
        }
    }
    
}
