package PersonalProject.demo.Implementation;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import PersonalProject.demo.Dto.Request.CreateInventoryItemRequest;
import PersonalProject.demo.Dto.Response.InventoryItemInventory;
import PersonalProject.demo.Dto.Response.InventoryItemProduct;
import PersonalProject.demo.Enums.ErrorCode;
import PersonalProject.demo.exception.ResourceNotFoundException;
import PersonalProject.demo.exception.TenantException;
import PersonalProject.demo.models.Inventory;
import PersonalProject.demo.models.InventoryItem;
import PersonalProject.demo.models.Products;
import PersonalProject.demo.repositories.InventoryItemRepository;
import PersonalProject.demo.repositories.InventoryRepository;
import PersonalProject.demo.repositories.ProductRepository;
import PersonalProject.demo.services.InventoryItemService;
import PersonalProject.demo.utils.TenantUtil;
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
    public void AddProductsToInventory(CreateInventoryItemRequest createRequest, Long inventoryId, Long tenantId) {
        tenantUtil.validateTenant(tenantId);
        System.out.println("------check find inventory--------");
        System.out.println("-----inventoryId: "+inventoryId);
        System.out.println("-----tenantId: "+tenantId);
        
        Inventory inventory = inventoryRepository.findByIdAndTenantId(inventoryId, tenantId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.Resource_not_found));
        System.out.println("------check find inventory done--------");
        
        for (Map.Entry<Long, Integer> entry : createRequest.getProductIdsAndQuantity().entrySet()) {
            Long productId = entry.getKey();
            Integer quantity = entry.getValue();

            System.out.println("------check find product--------");
            Products product = productRepository.findByIdAndTenantId(productId, tenantId)
                    .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.Resource_not_found));

            if (quantity == null || quantity < 0) {
                throw new IllegalArgumentException("Quantity must be non-negative for productId: " + productId);
            }

            InventoryItem existingItem = inventoryItemRepository.findByInventoryIdAndProductId(inventoryId, productId);

            if (existingItem != null) {
                existingItem.setQuantity(quantity);
                inventoryItemRepository.save(existingItem);
            } else {
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
    public void AddProductToInventories(CreateInventoryItemRequest createRequest, Long productId, Long tenantId) {
        tenantUtil.validateTenant(tenantId);
        Products product = productRepository.findByIdAndTenantId(productId, tenantId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.Resource_not_found));
        
        Integer quantity = createRequest.getQuantity();
        if (quantity == null || quantity < 0) {
            throw new IllegalArgumentException("Quantity must be non-negative for productId: " + productId);
        }

        Set<Long> inventoryIds = createRequest.getInventoryIds();
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

        for (Long inventoryId : inventoryIds) {
            Inventory inventory = inventoryMap.get(inventoryId);
            if (!inventory.getTenantId().equals(tenantId)) {
                throw new TenantException(ErrorCode.Tenant_Exception);
            }
            InventoryItem existingItem = inventoryItemRepository.findByInventoryIdAndProductId(inventoryId, productId);

            if (existingItem != null) {
                existingItem.setQuantity(quantity);
                inventoryItemRepository.save(existingItem);
            } else {
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
    public List<InventoryItemProduct> getProductsInInventory(Long inventoryId, Long tenantId) {
        tenantUtil.validateTenant(tenantId);
        Inventory inventory = inventoryRepository.findByIdAndTenantId(inventoryId, tenantId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.Resource_not_found));
        List<InventoryItem> inventoryItems = inventoryItemRepository.findAllByInventoryIdAndTenantId(inventory.getId(), tenantId);
        return inventoryItems.stream()
                .map(inventoryItem -> InventoryItemProduct.builder()
                        .id(inventoryItem.getProduct().getId())
                        .name(inventoryItem.getProduct().getName())
                        .sku(inventoryItem.getProduct().getSku())
                        .image(inventoryItem.getProduct().getImage())
                        .mrp(inventoryItem.getProduct().getMrp())
                        .sellingPrice(inventoryItem.getProduct().getSellingPrice())
                        .quantity(inventoryItem.getQuantity())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public List<InventoryItemInventory> getInventoriesByProductId(Long productId, Long tenantId) {
        tenantUtil.validateTenant(tenantId);
        Products product = productRepository.findByIdAndTenantId(productId, tenantId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.Resource_not_found));
        List<InventoryItem> inventoryItems = inventoryItemRepository.findAllByProductIdAndTenantId(productId, tenantId);
        return inventoryItems.stream()
                .map(inventoryItem -> InventoryItemInventory.builder()
                        .id(inventoryItem.getInventory().getId())
                        .inventoryName(inventoryItem.getInventory().getInventoryName())
                        .createdAt(inventoryItem.getInventory().getCreatedAt())
                        .updatedAt(inventoryItem.getInventory().getUpdatedAt())
                        .tenant_id(tenantId)
                        .branch_id(inventoryItem.getInventory().getBranch().getId())
                        .build())
                .collect(Collectors.toList());
    }
}