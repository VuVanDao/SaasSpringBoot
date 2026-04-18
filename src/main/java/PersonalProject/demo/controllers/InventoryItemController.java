package PersonalProject.demo.controllers;

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
import PersonalProject.demo.services.InventoryItemService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

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
            @RequestBody CreateInventoryItemRequest createRequest,  HttpServletRequest request2) {
        inventoryItemService.AddProductToInventories(createRequest, productId, request2);
        return ResponseEntity.ok().build();
    }
}
