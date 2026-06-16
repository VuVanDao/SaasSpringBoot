package PersonalProject.demo.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import PersonalProject.demo.Dto.Request.CreateInventoryRequest;
import PersonalProject.demo.Dto.Request.UpdateInventoryRequest;
import PersonalProject.demo.Dto.Response.ApiResponse;
import PersonalProject.demo.Dto.Response.InventoryDto;
import PersonalProject.demo.services.InventoryService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class InventoryController {
    private final InventoryService inventoryService;

    @PostMapping("/inventories")
    public ResponseEntity<ApiResponse<InventoryDto>> createInventory(
            @RequestBody CreateInventoryRequest request,
            @RequestHeader("${app.header-tenant}") Long tenantId) {
        InventoryDto inventory = inventoryService.createInventory(request, tenantId);
        ApiResponse<InventoryDto> response = ApiResponse.<InventoryDto>builder()
                .code(HttpStatus.CREATED.value())
                .result(inventory)
                .message("Inventory created successfully")
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/inventories/{id}")
    public ResponseEntity<ApiResponse<InventoryDto>> updateInventory(
            @PathVariable Long id,
            @RequestBody UpdateInventoryRequest request,
            @RequestHeader("${app.header-tenant}") Long tenantId) {
        InventoryDto inventory = inventoryService.updateInventory(id, request, tenantId);
        ApiResponse<InventoryDto> response = ApiResponse.<InventoryDto>builder()
                .code(HttpStatus.OK.value())
                .result(inventory)
                .message("Inventory updated successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/inventories/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteInventory(
            @PathVariable Long id,
            @RequestHeader("${app.header-tenant}") Long tenantId) {
        inventoryService.deleteInventory(id, tenantId);
        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .code(HttpStatus.OK.value())
                .message("Inventory deleted successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/inventories/{id}")
    public ResponseEntity<ApiResponse<InventoryDto>> getInventoryById(
            @PathVariable Long id,
            @RequestHeader("${app.header-tenant}") Long tenantId) {
        InventoryDto inventory = inventoryService.getInventoryById(id, tenantId);
        ApiResponse<InventoryDto> response = ApiResponse.<InventoryDto>builder()
                .code(HttpStatus.OK.value())
                .result(inventory)
                .message("Inventory retrieved successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/products/{productId}/inventories")
    public ResponseEntity<ApiResponse<InventoryDto>> getProductInInventory(@PathVariable Long productId) {
        InventoryDto inventory = inventoryService.getProductInInventory(productId);
        ApiResponse<InventoryDto> response = ApiResponse.<InventoryDto>builder()
                .code(HttpStatus.OK.value())
                .result(inventory)
                .message("Inventory retrieved successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/branches/{branchId}/inventories")
    public ResponseEntity<ApiResponse<List<InventoryDto>>> getAllInventoryByBranchId(
            @PathVariable Long branchId,
            @RequestHeader("${app.header-tenant}") Long tenantId) {
        List<InventoryDto> inventories = inventoryService.getAllInventoryByBranchId(branchId, tenantId);
        ApiResponse<List<InventoryDto>> response = ApiResponse.<List<InventoryDto>>builder()
                .code(HttpStatus.OK.value())
                .result(inventories)
                .message("Inventories retrieved successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/inventories")
    public ResponseEntity<ApiResponse<List<InventoryDto>>> getAllInventory() {
        List<InventoryDto> inventories = inventoryService.getAllInventory();
        ApiResponse<List<InventoryDto>> response = ApiResponse.<List<InventoryDto>>builder()
                .code(HttpStatus.OK.value())
                .result(inventories)
                .message("All inventories retrieved successfully")
                .build();
        return ResponseEntity.ok(response);
    }
}