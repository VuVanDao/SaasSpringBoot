package PersonalProject.demo.controllers;

import java.util.List;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import PersonalProject.demo.Dto.Request.CreateInventoryItemRequest;
import PersonalProject.demo.Dto.Response.ApiResponse;
import PersonalProject.demo.Dto.Response.InventoryItemInventory;
import PersonalProject.demo.Dto.Response.InventoryItemProduct;
import PersonalProject.demo.services.InventoryItemService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/inventory_item")
@RequiredArgsConstructor
public class InventoryItemController {
    private final InventoryItemService inventoryItemService;

    @PostMapping("/batch/{inventoryId}")
    public ApiResponse AddProductsToInventory(@RequestBody CreateInventoryItemRequest createRequest,
            @PathVariable Long inventoryId, HttpServletRequest request2) {
        inventoryItemService.AddProductsToInventory(createRequest, inventoryId, request2);
        System.out.println("REturn value:------------------");
        return ApiResponse.builder()
                .code(HttpStatus.CREATED.value())
                .result(null)
                .message("Add multi product to inventory successfully")
                .build();
    }
    @PostMapping("/product-batch/{productId}")
    public ResponseEntity<Void> addProductToInventories(
            @PathVariable Long productId,
            @RequestBody CreateInventoryItemRequest createRequest, HttpServletRequest request2) {
        inventoryItemService.AddProductToInventories(createRequest, productId, request2);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/get_products_from_inventory/{inventoryId}")
    public ApiResponse<List<InventoryItemProduct>> getProductsInInventory(@PathVariable Long inventoryId,
            HttpServletRequest request) {
        List<InventoryItemProduct> products = inventoryItemService.getProductsInInventory(inventoryId, request);
        return ApiResponse.<List<InventoryItemProduct>>builder()
                .code(HttpStatus.OK.value())
                .result(products)
                .message("Products in inventory retrieved successfully")
                .build();
    }
    @GetMapping("/get_inventories_from_product/{productId}")
    public ApiResponse<List<InventoryItemInventory>> getInventoriesByProductId(@PathVariable Long productId, HttpServletRequest request) {
        List<InventoryItemInventory> inventories = inventoryItemService.getInventoriesByProductId(productId, request);
        return ApiResponse.<List<InventoryItemInventory>>builder()
                .code(HttpStatus.OK.value())
                .result(inventories)
                .message("Inventories retrieved by product_id successfully")
                .build();
    }
}
