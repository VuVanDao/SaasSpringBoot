package PersonalProject.demo.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import PersonalProject.demo.Dto.Request.CreateInventoryRequest;
import PersonalProject.demo.Dto.Request.UpdateInventoryRequest;
import PersonalProject.demo.Dto.Response.ApiResponse;
import PersonalProject.demo.Dto.Response.InventoryDto;
import PersonalProject.demo.services.InventoryService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/inventory")
@RequiredArgsConstructor
public class InventoryController {
    private final InventoryService inventoryService;

    @PostMapping
    public ApiResponse<InventoryDto> createInventory(
            @RequestBody CreateInventoryRequest request,
            @RequestHeader("${app.header-tenant}") Long tenantId) {
        InventoryDto inventory = inventoryService.createInventory(request, tenantId);
        return ApiResponse.<InventoryDto>builder()
                .code(HttpStatus.CREATED.value())
                .result(inventory)
                .message("Inventory created successfully")
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<InventoryDto> updateInventory(
            @PathVariable Long id,
            @RequestBody UpdateInventoryRequest request,
            @RequestHeader("${app.header-tenant}") Long tenantId) {
        InventoryDto inventory = inventoryService.updateInventory(id, request, tenantId);
        return ApiResponse.<InventoryDto>builder()
                .code(HttpStatus.OK.value())
                .result(inventory)
                .message("Inventory updated successfully")
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteInventory(
            @PathVariable Long id,
            @RequestHeader("${app.header-tenant}") Long tenantId) {
        inventoryService.deleteInventory(id, tenantId);
        return ApiResponse.<Void>builder()
                .code(HttpStatus.OK.value())
                .message("Inventory deleted successfully")
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<InventoryDto> getInventoryById(
            @PathVariable Long id,
            @RequestHeader("${app.header-tenant}") Long tenantId) {
        InventoryDto inventory = inventoryService.getInventoryById(id, tenantId);
        return ApiResponse.<InventoryDto>builder()
                .code(HttpStatus.OK.value())
                .result(inventory)
                .message("Inventory retrieved successfully")
                .build();
    }

    @GetMapping("/products/{productId}")
    public ApiResponse<InventoryDto> getProductInInventory(@PathVariable Long productId) {
        InventoryDto inventory = inventoryService.getProductInInventory(productId);
        return ApiResponse.<InventoryDto>builder()
                .code(HttpStatus.OK.value())
                .result(inventory)
                .message("Inventory retrieved successfully")
                .build();
    }

    @GetMapping("/branches/{branchId}")
    public ApiResponse<List<InventoryDto>> getAllInventoryByBranchId(
            @PathVariable Long branchId,
            @RequestHeader("${app.header-tenant}") Long tenantId) {
        List<InventoryDto> inventories = inventoryService.getAllInventoryByBranchId(branchId, tenantId);
        return ApiResponse.<List<InventoryDto>>builder()
                .code(HttpStatus.OK.value())
                .result(inventories)
                .message("Inventories retrieved successfully")
                .build();
    }

    @GetMapping
    public ApiResponse<List<InventoryDto>> getAllInventory() {
        List<InventoryDto> inventories = inventoryService.getAllInventory();
        return ApiResponse.<List<InventoryDto>>builder()
                .code(HttpStatus.OK.value())
                .result(inventories)
                .message("All inventories retrieved successfully")
                .build();
    }
}