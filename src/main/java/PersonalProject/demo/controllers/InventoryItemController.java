package PersonalProject.demo.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import PersonalProject.demo.Dto.Request.CreateInventoryItemRequest;
import PersonalProject.demo.Dto.Response.ApiResponse;
import PersonalProject.demo.Dto.Response.InventoryItemInventory;
import PersonalProject.demo.Dto.Response.InventoryItemProduct;
import PersonalProject.demo.services.InventoryItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequiredArgsConstructor
public class InventoryItemController {
    private final InventoryItemService inventoryItemService;

    // DES: Thêm nhiều sản phẩm vào kho hàng cùng một lúc
    @PostMapping("/inventories/{inventoryId}/items")
    public ResponseEntity<ApiResponse<Void>> AddProductsToInventory(
             @RequestBody CreateInventoryItemRequest createRequest,
             @PathVariable Long inventoryId,
             @RequestHeader("${app.header-tenant}") Long tenantId) {
         inventoryItemService.AddProductsToInventory(createRequest, inventoryId, tenantId);
         ApiResponse<Void> response = ApiResponse.<Void>builder()
                 .code(HttpStatus.CREATED.value())
                 .result(null)
                 .message("Add multi product to inventory successfully")
                 .build();
         return ResponseEntity.status(HttpStatus.CREATED).body(response);
     }

    // DES: Thêm sản phẩm vào nhiều kho hàng
    @PostMapping("/products/{productId}/inventories")
    public ResponseEntity<ApiResponse<Void>> addProductToInventories(
             @PathVariable Long productId,
             @RequestBody CreateInventoryItemRequest createRequest,
             @RequestHeader("${app.header-tenant}") Long tenantId) {
         inventoryItemService.AddProductToInventories(createRequest, productId, tenantId);
         ApiResponse<Void> response = ApiResponse.<Void>builder()
                 .code(HttpStatus.CREATED.value())
                 .result(null)
                 .message("Product added to inventories successfully")
                 .build();
         return ResponseEntity.status(HttpStatus.CREATED).body(response);
     }

    // DES: Lấy danh sách các sản phẩm trong một kho hàng
    @GetMapping("/inventories/{inventoryId}/products")
    public ResponseEntity<ApiResponse<List<InventoryItemProduct>>> getProductsInInventory(
             @PathVariable Long inventoryId,
             @RequestHeader("${app.header-tenant}") Long tenantId) {
         List<InventoryItemProduct> products = inventoryItemService.getProductsInInventory(inventoryId, tenantId);
         ApiResponse<List<InventoryItemProduct>> response = ApiResponse.<List<InventoryItemProduct>>builder()
                 .code(HttpStatus.OK.value())
                 .result(products)
                 .message("Products in inventory retrieved successfully")
                 .build();
         return ResponseEntity.ok(response);
     }

    // DES: Lấy danh sách các kho hàng chứa sản phẩm cụ thể
    @GetMapping("/products/{productId}/inventories/items")
    public ResponseEntity<ApiResponse<List<InventoryItemInventory>>> getInventoriesByProductId(
            @PathVariable Long productId,
            @RequestHeader("${app.header-tenant}") Long tenantId) {
        List<InventoryItemInventory> inventories = inventoryItemService.getInventoriesByProductId(productId, tenantId);
        ApiResponse<List<InventoryItemInventory>> response = ApiResponse.<List<InventoryItemInventory>>builder()
                .code(HttpStatus.OK.value())
                .result(inventories)
                .message("Inventories retrieved by product_id successfully")
                .build();
        return ResponseEntity.ok(response);
    }
}
