package PersonalProject.demo.Implementation;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import PersonalProject.demo.Dto.Request.CreateInventoryItemRequest;
import PersonalProject.demo.Dto.Response.InventoryItemInventory;
import PersonalProject.demo.Dto.Response.InventoryItemProduct;
import PersonalProject.demo.Dto.Response.ProductDto;
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
                /*
                Collectors.toMap(keyMapper, valueMapper)
                Tham số thứ hai (inventory -> inventory): Đây là Value của Map.

                Nó giữ nguyên chính đối tượng Inventory đó làm giá trị.

                Biểu thức Lambda này có nghĩa là: "Với mỗi đối tượng inventory tìm thấy, hãy dùng chính nó làm giá trị trong Map".
                 */
                .collect(Collectors.toMap(Inventory::getId, inventory -> inventory));
        /*
        - collect
        Chức năng: Đây là một hàm "gom" dữ liệu. Sau khi xử lý xong các phần tử trong Stream, bạn cần gom chúng lại thành một cấu trúc dữ liệu mới
        */
        if (inventoryMap.size() != inventoryIds.size()) {
            Set<Long> foundIds = inventoryMap.keySet();
            Set<Long> missingIds = inventoryIds.stream()
                    .filter(id -> !foundIds.contains(id))
                    .collect(Collectors.toSet());
            throw new IllegalArgumentException("Inventory not found with ids: " + missingIds);
        }

        // 4. Xử lý từng inventory
        for (Long inventoryId : inventoryIds) {
            // lấy inventory từ map đã tạo ở bước 3 để tránh truy vấn lại database
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

    @Override
    public List<InventoryItemProduct> getProductsInInventory(Long inventoryId, HttpServletRequest request) {
        Long tenantId = tenantUtil.validateTenant(request);
        // 1. Kiểm tra inventory tồn tại
        Inventory inventory = inventoryRepository.findByIdAndTenantId(inventoryId, tenantId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.Resource_not_found));
        // 2. Lấy danh sách sản phẩm trong inventory
        List<InventoryItem> inventoryItems = inventoryItemRepository.findAllByInventoryIdAndTenantId(inventory.getId(), tenantId);
        // 3. Chuyển đổi thành danh sách InventoryItemProduct
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
    public List<InventoryItemInventory> getInventoriesByProductId(Long productId, HttpServletRequest request) {
        Long tenantId = tenantUtil.validateTenant(request);
        // 1. Kiểm tra product tồn tại
        Products product = productRepository.findByIdAndTenantId(productId, tenantId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.Resource_not_found));
        // 2. Lấy danh sách inventory chứa sản phẩm
        List<InventoryItem> inventoryItems = inventoryItemRepository.findAllByProductIdAndTenantId(productId, tenantId);
        // 3. Chuyển đổi thành danh sách InventoryItemInventory
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